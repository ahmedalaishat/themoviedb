package com.alaishat.ahmed.themoviedb.ui.feature.watchlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.alaishat.ahmed.themoviedb.presentation.common.model.Movie
import com.alaishat.ahmed.themoviedb.presentation.feature.watchlist.WatchListViewModel
import com.alaishat.ahmed.themoviedb.ui.R
import com.alaishat.ahmed.themoviedb.ui.common.EmptyContent
import com.alaishat.ahmed.themoviedb.ui.common.MovieListItemShimmer
import com.alaishat.ahmed.themoviedb.ui.common.movieInfoList
import com.alaishat.ahmed.themoviedb.ui.component.DevicePreviews
import com.alaishat.ahmed.themoviedb.ui.component.SearchBar
import com.alaishat.ahmed.themoviedb.ui.component.TheMoviePreviewSurface
import com.alaishat.ahmed.themoviedb.ui.extenstions.PagingEmptyBox
import com.alaishat.ahmed.themoviedb.ui.extenstions.PagingErrorBox
import com.alaishat.ahmed.themoviedb.ui.extenstions.pagingInitialLoader
import com.alaishat.ahmed.themoviedb.ui.theme.Dimensions
import kotlinx.coroutines.flow.flowOf

/**
 * Created by Ahmed Al-Aishat on Jun/17/2023.
 * The Movie DB Project.
 */
@Composable
fun WatchListRoute(
    onMovieClick: (movieId: Int) -> Unit,
    viewModel: WatchListViewModel = hiltViewModel(),
) {
    val query by viewModel.queryFlow.collectAsStateWithLifecycle()
    val pagingItems = viewModel.watchListFlow.collectAsLazyPagingItems()

    WatchListScreen(
        searchText = query,
        pagingItems = pagingItems,
        onSearchTextChange = viewModel::updateQueryText,
        onMovieClick = onMovieClick,
    )
}

@Composable
private fun WatchListScreen(
    searchText: String,
    pagingItems: LazyPagingItems<Movie>,
    onSearchTextChange: (String) -> Unit,
    onMovieClick: (movieId: Int) -> Unit,
) {

    PagingEmptyBox(
        pagingItems = pagingItems,
        modifier = Modifier.fillMaxSize(),
    ) {
        EmptyContent(
            imageId = R.drawable.no_items,
            title = stringResource(R.string.watch_list_no_movies_title),
            subtitle = stringResource(id = R.string.find_your_movie_by),
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimensions.ScreenPadding),
        )
    }

    PagingErrorBox(
        pagingItems = pagingItems,
        modifier = Modifier.fillMaxSize(),
    ) { error ->
        EmptyContent(
            imageId = R.drawable.error,
            title = stringResource(R.string.something_went_wrong),
            subtitle = error,
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimensions.ScreenPadding),
        )
    }

    LazyVerticalGrid(
        verticalArrangement = Arrangement.spacedBy(Dimensions.MarginLg),
        horizontalArrangement = Arrangement.spacedBy(Dimensions.MarginSm),
        contentPadding = PaddingValues(
            start = Dimensions.ScreenPadding,
            end = Dimensions.ScreenPadding,
            bottom = Dimensions.ScreenPadding,
        ),
        columns = GridCells.Adaptive(300.dp),
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            Surface(color = MaterialTheme.colorScheme.background) {
                SearchBar(
                    searchText = searchText,
                    placeholder = stringResource(R.string.search),
                    onSearchTextChange = onSearchTextChange,
                    modifier = Modifier
                        .padding(top = Dimensions.ScreenPadding)
                        .fillMaxWidth()
                )
            }
        }
        val itemModifier = Modifier.height(110.dp)

        pagingInitialLoader(pagingItems.loadState) {
            items(count = 10) { // Shimmer
                MovieListItemShimmer(modifier = itemModifier)
            }
        }

        movieInfoList(
            pagingItems = pagingItems,
            onMovieClick = onMovieClick,
            itemModifier = itemModifier,
        )
    }
}

@DevicePreviews
@Composable
private fun WatchListScreenPreview() {
    //AHMED_TODO: add preview parameters
    TheMoviePreviewSurface {
        WatchListScreen(
            searchText = "",
            pagingItems = flowOf(PagingData.empty<Movie>()).collectAsLazyPagingItems(),
            onSearchTextChange = { },
        ) { }
    }
}