package com.vk.directop.grandstore.detailcard.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vk.directop.grandstore.applist.presentation.AppItem
import com.vk.directop.grandstore.detailcard.domain.model.Game

@Composable
fun ShowcaseItem(
    pages: List<List<Game>>,
    onAppClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (pages.isEmpty()) return
    val pagerState = rememberPagerState(pageCount = { pages.size })
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Витрина",
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 10.dp),
        )
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
        ) { page ->
            ShowcasePage(
                games = pages[page],
                onAppClick = onAppClick,
            )
        }
    }
}

@Composable
private fun ShowcasePage(
    games: List<Game>,
    onAppClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        games.forEach { game ->
            AppItem(
                title = game.title,
                description = game.description,
                category = game.category,
                image = game.image,
                onClick = onAppClick,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ShowcaseItemPreview() {
    ShowcaseItem(
        pages = listOf(
            listOf(
                Game(
                    id = "guild-of-heroes",
                    title = "Гильдия Героев",
                    description = "Это очень интересная игра",
                    category = "RPG",
                    image = "https://www.film.ru/sites/default/files/styles/epsa_260x400/public/game/covers/1d84898b3a1fe0eeb0ac0fbb83cfff3c.jpg",
                ),
            ),
        ),
        onAppClick = {},
    )
}
