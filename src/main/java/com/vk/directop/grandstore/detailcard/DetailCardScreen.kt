package com.vk.directop.grandstore.detailcard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun DetailRoute(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(viewModel) {
        viewModel.effects.collect { effect ->
            when (effect) {
                DetailEffect.NavigateBack -> onBack()
            }
        }
    }
    DetailCardScreen(
        state = state,
        onAction = viewModel::onAction,
        modifier = modifier,
    )
}

@Composable
fun DetailCardScreen(
    state: DetailState,
    onAction: (DetailAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
    ) {
        CardColorsTopBar(
            onBackClick = { onAction(DetailAction.OnBackClick) },
            onShareClick = { onAction(DetailAction.OnShareClick) },
        )
        if (state.isMissing) {
            Text(text = "Игра не найдена")
            return@Column
        }
        PictureAndAppNameItem(
            title = state.title,
            category = state.category,
            imageUrl = state.imageUrl,
        )
        Button(
            onClick = { onAction(DetailAction.OnInstallClick) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2281CC),
                contentColor = Color.White
            )
        ) {
            Text(text = if (state.isInstalled) "Установлено" else "Установить")
        }
        ScreenShotsItem(imageUrl = state.imageUrl)
        AppDescriptionItem(
            description = state.description,
            isExpanded = state.isDescriptionExpanded,
            onToggle = { onAction(DetailAction.OnDescriptionClick) },
        )
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 24.dp),
            thickness = 1.dp,
            color = Color.LightGray
        )
        DeveloperItem()
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailedCardScreenPreview() {
    DetailCardScreen(
        state = DetailState(
            gameId = "guild-of-heroes",
            title = "Гильдия Героев: Экшен ММО РПГ",
            description = "Легендарный рейд героев в Фэнтези РПГ.",
            category = "Игры",
            imageUrl = "https://i0.wp.com/dictionaryblog.cambridge.org/wp-content/uploads/2026/09/climate.jpg?ssl=1",
        ),
        onAction = {}
    )
}
