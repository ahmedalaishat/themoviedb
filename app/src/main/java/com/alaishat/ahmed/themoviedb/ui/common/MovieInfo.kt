package com.alaishat.ahmed.themoviedb.ui.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.alaishat.ahmed.themoviedb.ui.component.SpacerXSm

/**
 * Created by Ahmed Al-Aishat on Jun/17/2023.
 * The Movie DB Project.
 */
@Composable
fun MovieInfo(
    @DrawableRes iconId: Int,
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            tint = color,
        )
        SpacerXSm()
        Text(text = text, color = color)
    }
}