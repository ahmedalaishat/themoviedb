package com.alaishat.ahmed.themoviedb.ui.common

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.alaishat.ahmed.themoviedb.domain.model.Movie
import com.alaishat.ahmed.themoviedb.feature.home.MovieListUiState

/**
 * Created by Ahmed Al-Aishat on Jun/17/2023.
 * The Movie DB Project.
 */
fun LazyListScope.topMoviesList(
    topMovies: MovieListUiState.Success,
    cardModifier: Modifier = Modifier,
) {
    itemsIndexed(topMovies.movies, key = { _, item -> item.id }) { index, movie ->
        TopMovieCard(
            movie = movie,
            rank = index + 1,
            modifier = cardModifier,
        )
    }
}

fun LazyGridScope.movieInfoList(
    pagingItems: LazyPagingItems<Movie>,
    itemModifier: Modifier = Modifier,
) = items(
    count = pagingItems.itemCount,
    key = pagingItems.itemKey(Movie::id),
    itemContent = { index ->
        val movie = pagingItems[index]!!
        MovieListItem(
            movie = movie,
            modifier = itemModifier,
        )
    },
)

fun LazyGridScope.movieCardsList(
    pagingItems: LazyPagingItems<Movie>,
    cardModifier: Modifier = Modifier,
) = items(
    count = pagingItems.itemCount,
    key = pagingItems.itemKey(Movie::id),
    itemContent = { index ->
        val movie = pagingItems[index]!!
        MovieCard(
            movie = movie,
            modifier = cardModifier,
        )
    }
)