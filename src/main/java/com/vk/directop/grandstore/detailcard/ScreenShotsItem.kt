package com.vk.directop.grandstore.detailcard

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun ScreenShotsItem() {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Скриншоты",
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 16.dp, bottom = 10.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AsyncImage(
                model = "https://i0.wp.com/dictionaryblog.cambridge.org/wp-content/uploads/2026/09/climate.jpg?ssl=1",
                contentDescription = "Screenshot",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(240.dp)
                    .height(140.dp)
                    .clip(RoundedCornerShape(16.dp)),
            )
            AsyncImage(
                model = "https://i0.wp.com/dictionaryblog.cambridge.org/wp-content/uploads/2026/09/climate.jpg?ssl=1",
                contentDescription = "Screenshot",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(240.dp)
                    .height(140.dp)
                    .clip(RoundedCornerShape(16.dp)),
            )
            AsyncImage(
                model = "https://i0.wp.com/dictionaryblog.cambridge.org/wp-content/uploads/2026/09/climate.jpg?ssl=1",
                contentDescription = "Screenshot",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(240.dp)
                    .height(140.dp)
                    .clip(RoundedCornerShape(16.dp)),
            )
            AsyncImage(
                model = "https://i0.wp.com/dictionaryblog.cambridge.org/wp-content/uploads/2026/09/climate.jpg?ssl=1",
                contentDescription = "Screenshot",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(240.dp)
                    .height(140.dp)
                    .clip(RoundedCornerShape(16.dp)),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenShotsItemPreview() {
    ScreenShotsItem()
}

