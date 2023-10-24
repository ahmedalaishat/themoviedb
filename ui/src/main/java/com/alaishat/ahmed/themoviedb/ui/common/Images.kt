package com.alaishat.ahmed.themoviedb.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import coil.request.ImageRequest

/**
 * Created by Ahmed Al-Aishat on Sep/09/2023.
 * The Movie DB Project.
 */
@Composable
fun imageRequest(data: Any?) =
    ImageRequest.Builder(LocalContext.current)
        .data(data)
        .crossfade(true)
        .build()