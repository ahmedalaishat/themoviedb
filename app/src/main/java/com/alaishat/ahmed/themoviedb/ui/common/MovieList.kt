package com.alaishat.ahmed.themoviedb.ui.common

import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.ui.Modifier
import com.alaishat.ahmed.themoviedb.domain.model.Movie

/**
 * Created by Ahmed Al-Aishat on Jun/17/2023.
 * The Movie DB Project.
 */
fun LazyGridScope.movieList(
    movies: List<Movie>,
    itemModifier: Modifier = Modifier,
) = items(
    items = movies,
    key = Movie::id,
    itemContent = { movie ->
        MovieListItem(
            movie = movie,
            modifier = itemModifier,
        )
    },
)

