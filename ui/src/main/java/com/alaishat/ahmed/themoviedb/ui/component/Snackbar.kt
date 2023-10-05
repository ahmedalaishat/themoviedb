package com.alaishat.ahmed.themoviedb.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Created by Ahmed Al-Aishat on Oct/03/2023.
 * The Movie DB Project.
 */
data class SnackbarVisualsCustom(
    override val message: String,
    override val actionLabel: String? = null,
    override val withDismissAction: Boolean = false,
    override val duration: SnackbarDuration = if (actionLabel == null) SnackbarDuration.Short else SnackbarDuration.Indefinite,
    val isOnline: Boolean
) : SnackbarVisuals

@Composable
fun ConnectionSnackBar(
    snackbarData: SnackbarData,
) {
    val visuals = snackbarData.visuals as? SnackbarVisualsCustom ?: return
    val containerColor = if (visuals.isOnline) Color(0xFF2CA641)
    else MaterialTheme.colorScheme.surfaceVariant

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(28.dp)
            .background(containerColor),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = visuals.message,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
        )
    }
}