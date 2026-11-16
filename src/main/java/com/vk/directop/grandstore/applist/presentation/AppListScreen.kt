package com.vk.directop.grandstore.applist.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vk.directop.grandstore.applist.domain.model.Game

@Composable
fun AppListRoute(
    onOpenDetail: (gameId: String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AppListViewModel = hiltViewModel(),
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
        val errorMessage = state.errorMessage
        when {
            state.isLoading && state.games.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }
            errorMessage != null && state.games.isEmpty() -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(text = errorMessage)
                    TextButton(onClick = { onAction(AppListAction.OnRetryClick) }) {
                        Text(text = "Повторить")
                    }
                }
            }
            else -> {
                RatingsLoadControls(
                    state = state,
                    onAction = onAction,
                )
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
                            rating = state.ratings[game.id],
                            onClick = { onAction(AppListAction.OnGameClick(game.id)) },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun RatingsLoadControls(
    state: AppListState,
    onAction: (AppListAction) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Button(
                onClick = { onAction(AppListAction.OnLoadRatingsSequentially) },
                modifier = Modifier.weight(1f),
                enabled = state.games.isNotEmpty(),
            ) {
                Text(text = "Последовательно", fontSize = 12.sp)
            }
            Button(
                onClick = { onAction(AppListAction.OnLoadRatingsInParallel) },
                modifier = Modifier.weight(1f),
                enabled = state.games.isNotEmpty(),
            ) {
                Text(text = "Параллельно", fontSize = 12.sp)
            }
        }
        val durationMs = state.ratingsDurationMs
        val mode = state.ratingsMode
        when {
            state.isLoadingRatings -> {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp)
                    Text(
                        text = if (mode == RatingsLoadMode.Sequential) {
                            "Последовательная загрузка ${state.games.size} рейтингов…"
                        } else {
                            "Параллельная загрузка ${state.games.size} рейтингов…"
                        },
                        fontSize = 12.sp,
                    )
                }
            }
            durationMs != null && mode != null -> {
                Text(
                    text = "${mode.title}: $durationMs мс",
                    fontSize = 12.sp,
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
