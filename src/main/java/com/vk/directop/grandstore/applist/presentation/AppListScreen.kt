package com.vk.directop.grandstore.applist.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vk.directop.grandstore.applist.domain.model.Game

@Composable
fun AppListRoute(
    onOpenDetail: (gameId: String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AppListViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(viewModel) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is AppListEffect.OpenDetail -> onOpenDetail(effect.gameId)
            }
        }
    }
    AppListScreen(
        state = state,
        onAction = viewModel::onAction,
        modifier = modifier,
    )
}

@Composable
fun AppListScreen(
    state: AppListState,
    onAction: (AppListAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        ListTopBar()
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(
                items = state.games,
                key = { game -> game.id },
            ) { game ->
                AppItem(
                    title = game.title,
                    description = game.description,
                    category = game.category,
                    image = game.image,
                    onClick = { onAction(AppListAction.OnGameClick(game.id)) },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AppListScreenPreview() {
    AppListScreen(
        state = AppListState(
            games = listOf(
                Game(
                    id = "guild-of-heroes",
                    title = "Гильдия Героев",
                    description = "Это очень интересная игра у неё можно играть постоянно, залипательная, с друзьями и с ботами",
                    category = "RPG",
                    image = "https://www.film.ru/sites/default/files/styles/epsa_260x400/public/game/covers/1d84898b3a1fe0eeb0ac0fbb83cfff3c.jpg",
                ),
            ),
        ),
        onAction = {},
    )
}
