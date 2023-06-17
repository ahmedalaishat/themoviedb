package com.alaishat.ahmed.themoviedb.ui.common

import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.ui.Modifier

/**
 * Created by Ahmed Al-Aishat on Jun/17/2023.
 * The Movie DB Project.
 */
//AHMED_TODO: add items list as parameter
fun LazyGridScope.movieList(
    itemModifier: Modifier = Modifier,
) = items(
    count = 10,
    itemContent = {
        MovieListItem(
            modifier = itemModifier,
            index = it,//AHMED_TODO:  remove me
        )
    }
)

