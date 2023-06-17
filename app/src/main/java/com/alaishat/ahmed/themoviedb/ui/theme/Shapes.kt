package com.alaishat.ahmed.themoviedb.ui.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.RectangleShape
import com.alaishat.ahmed.themoviedb.ui.theme.Dimensions.RadiusLg
import com.alaishat.ahmed.themoviedb.ui.theme.Dimensions.RadiusMd
import com.alaishat.ahmed.themoviedb.ui.theme.Dimensions.RadiusSm
import com.alaishat.ahmed.themoviedb.ui.theme.Dimensions.RadiusXLg
import com.alaishat.ahmed.themoviedb.ui.theme.Dimensions.RadiusXSm

/**
 * Created by Ahmed Al-Aishat on Jun/17/2023.
 * The Movie DB Project.
 */
object Shapes {
    val CornerFull = CircleShape
    val CornerExtraLarge = RoundedCornerShape(RadiusXLg)
    val CornerLarge = RoundedCornerShape(RadiusLg)
    val CornerMedium = RoundedCornerShape(RadiusMd)
    val CornerSmall = RoundedCornerShape(RadiusSm)
    val CornerExtraSmall = RoundedCornerShape(RadiusXSm)
    val CornerNone = RectangleShape
}