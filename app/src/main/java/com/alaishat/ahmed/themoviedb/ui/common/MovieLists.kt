package com.alaishat.ahmed.themoviedb.ui.common

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.alaishat.ahmed.themoviedb.domain.model.MovieDomainModel
import com.alaishat.ahmed.themoviedb.feature.home.MovieListUiState
import com.alaishat.ahmed.themoviedb.ui.extenstions.silentClickable

/**
 * Created by Ahmed Al-Aishat on Jun/17/2023.
 * The Movie DB Project.
 */
fun LazyListScope.topMoviesList(
    topMovies: MovieListUiState.Success,
    onMovieClick: (movieId: Int) -> Unit,
    cardModifier: Modifier = Modifier,
) {
    itemsIndexed(topMovies.movieDomainModels, key = { _, item -> item.id }) { index, movie ->
        TopMovieCard(
            movieDomainModel = movie,
            rank = index + 1,
            modifier = cardModifier.then(Modifier.silentClickable { onMovieClick(movie.id) }),
        )
    }
}

fun LazyGridScope.movieInfoList(
    pagingItems: LazyPagingItems<MovieDomainModel>,
    onMovieClick: (movieId: Int) -> Unit,
    itemModifier: Modifier = Modifier,
) = items(
    count = pagingItems.itemCount,
    key = pagingItems.itemKey(MovieDomainModel::id),
    itemContent = { index ->
        val movie = pagingItems[index]!!
        MovieListItem(
            movieDomainModel = movie,
            modifier = itemModifier.then(Modifier.silentClickable { onMovieClick(movie.id) }),
        )
    },
)

fun LazyGridScope.movieCardsList(
    pagingItems: LazyPagingItems<MovieDomainModel>,
    onMovieClick: (movieId: Int) -> Unit,
    cardModifier: Modifier = Modifier,
) = items(
    count = pagingItems.itemCount,
    key = pagingItems.itemKey(MovieDomainModel::id),
    itemContent = { index ->
        val movie = pagingItems[index] ?: return@items

        MovieCard(
            moviePosterPath = movie.posterPath,
            modifier = cardModifier.then(Modifier.silentClickable { onMovieClick(movie.id) }),
        )
    }
)