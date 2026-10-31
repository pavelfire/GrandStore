package com.vk.directop.grandstore.detailcard.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.vk.directop.grandstore.detailcard.data.repository.GamesRepositoryImpl
import com.vk.directop.grandstore.detailcard.domain.model.Game
import com.vk.directop.grandstore.detailcard.domain.repository.GamesRepository
import com.vk.directop.grandstore.navigation.DetailDestination
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DetailState(
    val gameId: String = "",
    val title: String = "",
    val description: String = "",
    val category: String = "",
    val imageUrl: String = "",
    val isDescriptionExpanded: Boolean = false,
    val isInstalled: Boolean = false,
    val isMissing: Boolean = false,
    val showcasePages: List<List<Game>> = emptyList(),
)

sealed interface DetailAction {
    data object OnBackClick : DetailAction
    data object OnInstallClick : DetailAction
    data object OnShareClick : DetailAction
    data object OnDescriptionClick : DetailAction
    data object OnShowcaseAppClick : DetailAction
}

sealed interface DetailEffect {
    data object NavigateBack : DetailEffect
    data object NavigateToAppList : DetailEffect
}

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val gamesRepository: GamesRepository = GamesRepositoryImpl()

    private val destination = savedStateHandle.toRoute<DetailDestination>()

    private val _state = MutableStateFlow(stateFor(destination.gameId))
    val state: StateFlow<DetailState> = _state.asStateFlow()

    private val _effects = Channel<DetailEffect>(Channel.BUFFERED)
    val effects: Flow<DetailEffect> = _effects.receiveAsFlow()

    fun onAction(action: DetailAction) {
        when (action) {
            DetailAction.OnBackClick -> viewModelScope.launch {
                _effects.send(DetailEffect.NavigateBack)
            }
            DetailAction.OnInstallClick -> _state.update { it.copy(isInstalled = !it.isInstalled) }
            DetailAction.OnShareClick -> Unit
            DetailAction.OnDescriptionClick -> _state.update {
                it.copy(isDescriptionExpanded = !it.isDescriptionExpanded)
            }
            DetailAction.OnShowcaseAppClick -> viewModelScope.launch {
                _effects.send(DetailEffect.NavigateToAppList)
            }
        }
    }

    private fun stateFor(gameId: String): DetailState {
        val showcasePages = gamesRepository.getShowcasePages()
        val game = gamesRepository.getGameById(gameId)
            ?: return DetailState(
                gameId = gameId,
                isMissing = true,
                showcasePages = showcasePages,
            )
        return DetailState(
            gameId = game.id,
            title = game.title,
            description = game.description,
            category = game.category,
            imageUrl = game.image,
            showcasePages = showcasePages,
        )
    }
}
