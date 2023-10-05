package com.alaishat.ahmed.themoviedb.ui.extenstions

import android.animation.TimeInterpolator
import androidx.compose.animation.core.Easing

/**
 * Created by Ahmed Al-Aishat on Sep/09/2023.
 * The Movie DB Project.
 */
fun TimeInterpolator.toEasing() = Easing { x ->
    getInterpolation(x)
}