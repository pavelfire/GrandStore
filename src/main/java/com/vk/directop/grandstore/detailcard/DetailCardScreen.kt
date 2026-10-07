package com.vk.directop.grandstore.detailcard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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
        PictureAndAppNameItem()
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

@Preview(showBackground = true)
@Composable
private fun DetailedCardScreenPreview() {
    DetailCardScreen()
}
