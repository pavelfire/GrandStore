package com.vk.directop.grandstore.applist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vk.directop.grandstore.applist.domain.model.Game
import com.vk.directop.grandstore.applist.domain.repository.GamesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.system.measureTimeMillis
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class RatingsLoadMode(val title: String) {
    Sequential("Последовательно"),
    Parallel("Параллельно"),
}

data class AppListState(
    val games: List<Game> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val ratings: Map<String, Float> = emptyMap(),
    val isLoadingRatings: Boolean = false,
    val ratingsMode: RatingsLoadMode? = null,
    val ratingsDurationMs: Long? = null,
)

sealed interface AppListAction {
    data class OnGameClick(val gameId: String) : AppListAction
    data object OnRetryClick : AppListAction
    data object OnLoadRatingsSequentially : AppListAction
    data object OnLoadRatingsInParallel : AppListAction
}

sealed interface AppListEffect {
    data class OpenDetail(val gameId: String) : AppListEffect
}

@HiltViewModel
class AppListViewModel @Inject constructor(
    private val gamesRepository: GamesRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(AppListState(isLoading = true))
    val state: StateFlow<AppListState> = _state.asStateFlow()

    private val _effects = Channel<AppListEffect>(Channel.BUFFERED)
    val effects: Flow<AppListEffect> = _effects.receiveAsFlow()

    private var ratingsJob: Job? = null
    private var ratingsGeneration = 0

    init {
        loadGames()
    }

    fun onAction(action: AppListAction) {
        when (action) {
            is AppListAction.OnGameClick -> viewModelScope.launch {
                _effects.send(AppListEffect.OpenDetail(action.gameId))
            }
            AppListAction.OnRetryClick -> loadGames()
            AppListAction.OnLoadRatingsSequentially -> loadRatings(RatingsLoadMode.Sequential)
            AppListAction.OnLoadRatingsInParallel -> loadRatings(RatingsLoadMode.Parallel)
        }
    }

    private fun loadGames() {
        ratingsGeneration += 1
        ratingsJob?.cancel()
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null,
                    ratings = emptyMap(),
                    isLoadingRatings = false,
                    ratingsDurationMs = null,
                    ratingsMode = null,
                )
            }
            try {
                val games = gamesRepository.getGames()
                _state.update { it.copy(games = games, isLoading = false) }
            } catch (error: CancellationException) {
                throw error
            } catch (_: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Не удалось загрузить каталог",
                    )
                }
            }
        }
    }

    private fun loadRatings(mode: RatingsLoadMode) {
        val ids = _state.value.games.map(Game::id)
        if (ids.isEmpty()) return

        val generation = ++ratingsGeneration
        ratingsJob?.cancel()
        ratingsJob = viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoadingRatings = true,
                    ratings = emptyMap(),
                    ratingsDurationMs = null,
                    ratingsMode = mode,
                )
            }
            try {
                var ratings = emptyMap<String, Float>()
                val durationMs = measureTimeMillis {
                    ratings = when (mode) {
                        RatingsLoadMode.Sequential -> gamesRepository.loadRatingsSequentially(ids)
                        RatingsLoadMode.Parallel -> gamesRepository.loadRatingsInParallel(ids)
                    }
                }
                if (generation == ratingsGeneration) {
                    _state.update {
                        it.copy(
                            ratings = ratings,
                            isLoadingRatings = false,
                            ratingsDurationMs = durationMs,
                            ratingsMode = mode,
                        )
                    }
                }
            } catch (error: CancellationException) {
                if (generation == ratingsGeneration) {
                    _state.update { it.copy(isLoadingRatings = false) }
                }
                throw error
            }
        }
    }
}
