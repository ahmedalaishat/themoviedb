package com.alaishat.ahmed.themoviedb.ui.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

/**
 * Created by Ahmed Al-Aishat on Jun/17/2023.
 * The Movie DB Project.
 */
@Composable
fun RowScope.RowDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = DividerDefaults.Thickness,
    color: Color = DividerDefaults.color,
) {
    Divider(
        modifier = modifier
            .fillMaxHeight()
            .width(thickness),
        thickness = thickness,
        color = color
    )
}