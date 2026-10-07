package com.vk.directop.grandstore.detailcard

import android.R.attr.thickness
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun DetailCardScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        CardColorsTopBar()
        AppDescription()
        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2281CC),
                contentColor = Color.White
            )
        ) {
            Text(text = "Установить")
        }
        ScreenShotsItem()
        AppDescriptionItem()
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 24.dp),
            thickness = 1.dp,
            color = Color.LightGray
        )
        DeveloperItem()
    }
}

@Composable
fun CardColorsTopBar(
    onBackClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { onBackClick() }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Go back"
            )
        }
        IconButton(onClick = { onShareClick() }) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = "Go back"
            )
        }
    }
}

@Composable
fun AppDescription() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AsyncImage(
            model = "https://i0.wp.com/dictionaryblog.cambridge.org/wp-content/uploads/2026/09/climate.jpg?ssl=1",
            contentDescription = "App icon",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
                .clip(RoundedCornerShape(16.dp)),
        )
        Spacer(Modifier.width(16.dp))
        Column(

        ) {
            Text(
                text = "Игры",
                color = Color.Gray
            )
            Text(
                text = "Гильдия Героев: Экшен ММО РПГ",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
            Text(
                text = "VK Play",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
            Spacer(Modifier.height(10.dp))
            Row() {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "12+",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "Возраст",
                        fontSize = 12.sp
                    )
                }
                Spacer(Modifier.width(16.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "224 MB",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "Размер",
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailedCardScreenPreview() {
    DetailCardScreen()
}
