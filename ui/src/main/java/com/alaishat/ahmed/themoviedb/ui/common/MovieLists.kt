package com.alaishat.ahmed.themoviedb.ui.common

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.alaishat.ahmed.themoviedb.presentation.common.model.Movie
import com.alaishat.ahmed.themoviedb.presentation.feature.home.model.MovieListViewState
import com.alaishat.ahmed.themoviedb.ui.extenstions.silentClickable

/**
 * Created by Ahmed Al-Aishat on Jun/17/2023.
 * The Movie DB Project.
 */
fun LazyListScope.topMoviesList(
    topMovies: MovieListViewState.Success,
    onMovieClick: (movieId: Int) -> Unit,
    cardModifier: Modifier = Modifier,
) {
    itemsIndexed(topMovies.movies, key = { _, item -> item.id }) { index, movie ->
        TopMovieCard(
            movie = movie,
            rank = index + 1,
            modifier = cardModifier.then(Modifier.silentClickable { onMovieClick(movie.id) }),
        )
    }
}

fun LazyGridScope.movieInfoList(
    pagingItems: LazyPagingItems<Movie>,
    onMovieClick: (movieId: Int) -> Unit,
    itemModifier: Modifier = Modifier,
) = items(
    count = pagingItems.itemCount,
    key = pagingItems.itemKey(Movie::id),
    itemContent = { index ->
        val movie = pagingItems[index]!!
        MovieListItem(
            movie = movie,
            modifier = itemModifier.then(Modifier.silentClickable { onMovieClick(movie.id) }),
        )
    },
)

fun LazyGridScope.movieCardsList(
    pagingItems: LazyPagingItems<Movie>,
    onMovieClick: (movieId: Int) -> Unit,
    cardModifier: Modifier = Modifier,
) = items(
    count = pagingItems.itemCount,
    //AHMED_TODO: the api keeps duplicate keys
//    key = pagingItems.itemKey(MovieDomainModel::id),
    itemContent = { index ->
        val movie = pagingItems[index] ?: return@items

        MovieCard(
            moviePosterPath = movie.posterPath,
            modifier = cardModifier.then(Modifier.silentClickable { onMovieClick(movie.id) }),
        )
    }
)