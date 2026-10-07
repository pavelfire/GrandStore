package com.vk.directop.grandstore.detailcard

import android.R.attr.top
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppDescriptionItem() {
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .clickable { isExpanded = !isExpanded }
    ) {
        Spacer(Modifier.height(16.dp))
        Text(
            text = "Описание приложения",
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )
        Text(
            text = "Легендарный рейд героев в Фэнтези РПГ. Здесь может быть ваш очень длинный текст описания приложения, который занимает много строк на экране. Когда пользователь нажимает на этот блок, текст раскрывается полностью, показывая все детали и особенности игры, которые изначально были скрыты под катом для экономии места.",
            fontSize = 14.sp,
            maxLines = if (isExpanded) Int.MAX_VALUE else 2,
            modifier = Modifier.padding(top = 10.dp)
        )
        Text(
            text = if (isExpanded) "Свернуть" else "Подробнее",
            fontSize = 14.sp,
            color = Color(0xFF2281CC),
            modifier = Modifier.padding(top = 10.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AppDescriptionItemPreview() {
    AppDescriptionItem()
}
