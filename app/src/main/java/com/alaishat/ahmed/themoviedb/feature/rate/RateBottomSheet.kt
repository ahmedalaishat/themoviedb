package com.alaishat.ahmed.themoviedb.feature.rate

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alaishat.ahmed.themoviedb.ui.component.DevicePreviews
import com.alaishat.ahmed.themoviedb.ui.component.SpacerMd
import com.alaishat.ahmed.themoviedb.ui.component.TheMoviePreviewSurface
import com.alaishat.ahmed.themoviedb.ui.theme.Dimensions

/**
 * Created by Ahmed Al-Aishat on Jun/18/2023.
 * The Movie DB Project.
 */
@Composable
fun RateBottomSheetContent() {
    var rate by remember { mutableStateOf(5) }

    Surface {
        Box(
            modifier = Modifier
                .padding(Dimensions.ScreenPadding.times(3))
                .wrapContentSize(),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.align(Alignment.TopEnd),
                imageVector = Icons.Default.Close,
                contentDescription = "close"
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Rate this movie")
                SpacerMd()
                Text(text = rate.toString())
                SpacerMd()
                Slider(
                    value = rate.toFloat(),
                    onValueChange = { rate = it.toInt() },
                    valueRange = 1f..10f,
                    steps = 10,
                    colors = SliderDefaults.colors(
                        thumbColor = MaterialTheme.colorScheme.secondary,
                        activeTrackColor = MaterialTheme.colorScheme.secondary,
                        activeTickColor = MaterialTheme.colorScheme.secondary,
                        inactiveTrackColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        inactiveTickColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier.widthIn(max = 400.dp),
                )
                SpacerMd()
                Button(
                    modifier = Modifier.width(220.dp),
                    onClick = { /*TODO*/ }) {
                    Text(text = "Ok")
                }
            }
        }
    }
}

@DevicePreviews
@Composable
fun RateBottomSheetPreview() {
    TheMoviePreviewSurface {
        RateBottomSheetContent()
    }
}