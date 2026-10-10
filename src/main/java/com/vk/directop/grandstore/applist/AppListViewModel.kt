package com.vk.directop.grandstore.applist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

data class AppListState(
    val games: List<Game> = emptyList()
)

sealed interface AppListAction {
    data class OnGameClick(val gameId: String) : AppListAction
}

sealed interface AppListEffect {
    data class OpenDetail(val gameId: String) : AppListEffect
}

class AppListViewModel : ViewModel() {
    private val _state = MutableStateFlow(AppListState(games = GamesCatalog.games))
    val state: StateFlow<AppListState> = _state.asStateFlow()

    private val _effects = Channel<AppListEffect>(Channel.BUFFERED)
    val effects: Flow<AppListEffect> = _effects.receiveAsFlow()

    fun onAction(action: AppListAction) {
        when (action) {
            is AppListAction.OnGameClick -> viewModelScope.launch {
                _effects.send(AppListEffect.OpenDetail(action.gameId))
            }
        }
    }
}
