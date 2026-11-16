package com.vk.directop.grandstore.detailcard.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.vk.directop.grandstore.detailcard.domain.model.Game
import com.vk.directop.grandstore.detailcard.domain.repository.GamesRepository
import com.vk.directop.grandstore.navigation.DetailDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.coroutines.coroutineContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException

sealed interface InstallState {
    data object NotInstalled : InstallState
    data class Installing(val progress: Int) : InstallState
    data object Installed : InstallState
}

data class DetailState(
    val gameId: String = "",
    val title: String = "",
    val description: String = "",
    val category: String = "",
    val imageUrl: String = "",
    val developer: String = "",
    val ageRating: Int = 0,
    val sizeMb: Double = 0.0,
    val screenshotUrls: List<String> = emptyList(),
    val isDescriptionExpanded: Boolean = false,
    val installState: InstallState = InstallState.NotInstalled,
    val isLoading: Boolean = false,
    val isMissing: Boolean = false,
    val errorMessage: String? = null,
    val showcasePages: List<List<Game>> = emptyList(),
)

sealed interface DetailAction {
    data object OnBackClick : DetailAction
    data object OnInstallClick : DetailAction
    data object OnShareClick : DetailAction
    data object OnDescriptionClick : DetailAction
    data object OnShowcaseAppClick : DetailAction
    data object OnRetryClick : DetailAction
}

sealed interface DetailEffect {
    data object NavigateBack : DetailEffect
    data object NavigateToAppList : DetailEffect
}

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val gamesRepository: GamesRepository,
) : ViewModel() {

    private val destination = savedStateHandle.toRoute<DetailDestination>()

    private val _state = MutableStateFlow(
        DetailState(gameId = destination.gameId, isLoading = true),
    )
    val state: StateFlow<DetailState> = _state.asStateFlow()

    private val _effects = Channel<DetailEffect>(Channel.BUFFERED)
    val effects: Flow<DetailEffect> = _effects.receiveAsFlow()

    private var installJob: Job? = null

    init {
        loadDetails(destination.gameId)
    }

    fun onAction(action: DetailAction) {
        when (action) {
            DetailAction.OnBackClick -> viewModelScope.launch {
                _effects.send(DetailEffect.NavigateBack)
            }
            DetailAction.OnInstallClick -> onInstallClick()
            DetailAction.OnShareClick -> Unit
            DetailAction.OnDescriptionClick -> _state.update {
                it.copy(isDescriptionExpanded = !it.isDescriptionExpanded)
            }
            DetailAction.OnShowcaseAppClick -> viewModelScope.launch {
                _effects.send(DetailEffect.NavigateToAppList)
            }
            DetailAction.OnRetryClick -> loadDetails(destination.gameId)
        }
    }

    private fun onInstallClick() {
        val current = _state.value
        if (current.isLoading || current.isMissing || current.errorMessage != null) return

        if (current.installState is InstallState.Installing) {
            installJob?.cancel()
            _state.update { it.copy(installState = InstallState.NotInstalled) }
            return
        }

        _state.update { it.copy(installState = InstallState.Installing(progress = 0)) }
        val job = viewModelScope.launch {
            emulateInstall()
        }
        installJob = job
        job.invokeOnCompletion { cause ->
            if (cause != null && installJob === job) {
                _state.update { it.copy(installState = InstallState.NotInstalled) }
            }
        }
    }

    private suspend fun emulateInstall() {
        for (progress in 0..100 step INSTALL_PROGRESS_STEP) {
            delay(INSTALL_PROGRESS_DELAY_MS)
            coroutineContext.ensureActive()
            _state.update { state ->
                if (state.installState !is InstallState.Installing) {
                    state
                } else {
                    state.copy(installState = InstallState.Installing(progress))
                }
            }
        }
        coroutineContext.ensureActive()
        _state.update { state ->
            if (state.installState !is InstallState.Installing) {
                state
            } else {
                state.copy(installState = InstallState.Installed)
            }
        }
    }

    private fun loadDetails(gameId: String) {
        installJob?.cancel()
        installJob = null
        viewModelScope.launch {
            _state.update {
                it.copy(
                    gameId = gameId,
                    isLoading = true,
                    isMissing = false,
                    errorMessage = null,
                    installState = InstallState.NotInstalled,
                )
            }
            val showcasePages = gamesRepository.getShowcasePages()
            try {
                val details = gamesRepository.getAppDetails(gameId)
                _state.update {
                    it.copy(
                        isLoading = false,
                        gameId = details.id,
                        title = details.name,
                        description = details.description,
                        category = details.category,
                        imageUrl = details.iconUrl,
                        developer = details.developer,
                        ageRating = details.ageRating,
                        sizeMb = details.size,
                        screenshotUrls = details.screenshotUrls,
                        showcasePages = showcasePages,
                    )
                }
            } catch (error: CancellationException) {
                throw error
            } catch (error: HttpException) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        isMissing = error.code() == 404,
                        errorMessage = if (error.code() == 404) null else LOAD_ERROR,
                        showcasePages = showcasePages,
                    )
                }
            } catch (_: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = LOAD_ERROR,
                        showcasePages = showcasePages,
                    )
                }
            }
        }
    }

    private companion object {
        const val LOAD_ERROR = "Не удалось загрузить приложение"
        const val INSTALL_PROGRESS_STEP = 5
        const val INSTALL_PROGRESS_DELAY_MS = 150L
    }
}
