package com.alaishat.ahmed.themoviedb.ui.component

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alaishat.ahmed.themoviedb.ui.theme.Dimensions

/**
 * Created by Ahmed Al-Aishat on Jun/17/2023.
 * The Movie DB Project.
 */
@Composable
fun ColumnScope.SpacerSm() {
    Spacer(modifier = Modifier.height(Dimensions.MarginSm))
}

@Composable
fun ColumnScope.SpacerMd() {
    Spacer(modifier = Modifier.height(Dimensions.MarginMd))
}

@Composable
fun ColumnScope.SpacerLg() {
    Spacer(modifier = Modifier.height(Dimensions.MarginLg))
}

@Composable
fun RowScope.SpacerSm() {
    Spacer(modifier = Modifier.width(Dimensions.MarginSm))
}

@Composable
fun RowScope.SpacerMd() {
    Spacer(modifier = Modifier.width(Dimensions.MarginMd))
}

@Composable
fun RowScope.SpacerLg() {
    Spacer(modifier = Modifier.width(Dimensions.MarginLg))
}