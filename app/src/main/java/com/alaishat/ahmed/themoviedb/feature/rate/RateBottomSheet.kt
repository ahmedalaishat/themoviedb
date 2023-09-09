package com.alaishat.ahmed.themoviedb.feature.rate

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alaishat.ahmed.themoviedb.R
import com.alaishat.ahmed.themoviedb.ui.component.AppButton
import com.alaishat.ahmed.themoviedb.ui.component.DevicePreviews
import com.alaishat.ahmed.themoviedb.ui.component.SpacerMd
import com.alaishat.ahmed.themoviedb.ui.component.TheMoviePreviewSurface
import com.alaishat.ahmed.themoviedb.ui.theme.Dimensions

/**
 * Created by Ahmed Al-Aishat on Jun/18/2023.
 * The Movie DB Project.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RateBottomSheet(
    onRateSubmit: (rating: Int) -> Unit,
    onDismiss: () -> Unit,
) {
    val modalBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = modalBottomSheetState,
    ) {
        RateBottomSheetContent(
            onRateSubmit = onRateSubmit
        )
    }
}

@Composable
private fun RateBottomSheetContent(
    onRateSubmit: (rating: Int) -> Unit,
) {
    var rating by remember { mutableStateOf(5) }
    var loading by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .padding(Dimensions.ScreenPadding)
            .wrapContentSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Rate this movie", color = MaterialTheme.colorScheme.background)
            SpacerMd()
            Text(text = rating.toString(), color = MaterialTheme.colorScheme.background)
            SpacerMd()
            Slider(
                value = rating.toFloat(),
                onValueChange = {
                    rating = if (it > 1) it.toInt() else 1
                },
                valueRange = 0f..10f,
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
            AppButton(
                modifier = Modifier.width(220.dp),
                onClick = {
                    loading = true
                    onRateSubmit(rating)
                },
                loading = loading
            ) {
                Text(text = stringResource(id = R.string.ok))
            }
        }
    }
}

@DevicePreviews
@Composable
fun RateBottomSheetPreview() {
    TheMoviePreviewSurface {
        Surface {
            RateBottomSheetContent(
                onRateSubmit = { },
            )
        }
    }
}