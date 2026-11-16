package com.vk.directop.grandstore.applist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vk.directop.grandstore.applist.domain.model.Game
import com.vk.directop.grandstore.applist.domain.repository.GamesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AppListState(
    val games: List<Game> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)

sealed interface AppListAction {
    data class OnGameClick(val gameId: String) : AppListAction
    data object OnRetryClick : AppListAction
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

    init {
        loadGames()
    }

    fun onAction(action: AppListAction) {
        when (action) {
            is AppListAction.OnGameClick -> viewModelScope.launch {
                _effects.send(AppListEffect.OpenDetail(action.gameId))
            }
            AppListAction.OnRetryClick -> loadGames()
        }
    }

    private fun loadGames() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
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
}
