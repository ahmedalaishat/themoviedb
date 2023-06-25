package com.alaishat.ahmed.themoviedb.ui.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.alaishat.ahmed.themoviedb.ui.component.SpacerMd

/**
 * Created by Ahmed Al-Aishat on Jun/17/2023.
 * The Movie DB Project.
 */
@Composable
fun EmptyContent(
    @DrawableRes imageId: Int,
    title: String,
    subtitle: String?,
    modifier: Modifier = Modifier,
    actionButtonText: String? = null,
    onActionButtonClick: () -> Unit = { },
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier.size(75.dp),
            painter = painterResource(id = imageId),
            contentDescription = null
        )
        Text(
            text = title,
            textAlign = TextAlign.Center,
        )
        if (subtitle != null)
            Text(
                text = subtitle,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        if (actionButtonText != null) {
            SpacerMd()
            OutlinedButton(onClick = onActionButtonClick) {
                Text(text = actionButtonText)
            }
        }
    }
}