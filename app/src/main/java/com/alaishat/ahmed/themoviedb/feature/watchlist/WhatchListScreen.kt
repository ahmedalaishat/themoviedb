package com.alaishat.ahmed.themoviedb.feature.watchlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alaishat.ahmed.themoviedb.R
import com.alaishat.ahmed.themoviedb.ui.common.EmptyContent
import com.alaishat.ahmed.themoviedb.ui.common.movieList
import com.alaishat.ahmed.themoviedb.ui.component.DevicePreviews
import com.alaishat.ahmed.themoviedb.ui.component.SearchBar
import com.alaishat.ahmed.themoviedb.ui.component.TheMoviePreviewSurface
import com.alaishat.ahmed.themoviedb.ui.theme.Dimensions

/**
 * Created by Ahmed Al-Aishat on Jun/17/2023.
 * The Movie DB Project.
 */
@Composable
fun WatchListRoute(
    viewModel: WatchListViewModel = hiltViewModel(),
) {
    val query by viewModel.queryFlow.collectAsStateWithLifecycle()
    val uiState by viewModel.searchMoviesFlow.collectAsStateWithLifecycle()

    WatchListScreen(
        searchText = query,
        uiState = uiState,
        onSearchTextChange = viewModel::updateQueryText
    )
}

@Composable
private fun WatchListScreen(
    searchText: String,
    uiState: WatchListUiState,
    onSearchTextChange: (String) -> Unit,
) {
    LazyVerticalGrid(
        verticalArrangement = Arrangement.spacedBy(Dimensions.MarginLg),
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
                    placeholder = "Search",
                    onSearchTextChange = onSearchTextChange,
                    modifier = Modifier
                        .padding(top = Dimensions.ScreenPadding)
                        .fillMaxWidth()
                )
            }
        }
        if (uiState is WatchListUiState.Success)
            movieList(
                movies = uiState.movies,
                itemModifier = Modifier.height(110.dp),
            )
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (uiState == WatchListUiState.Loading) CircularProgressIndicator()
        if (uiState == WatchListUiState.NoResults)
            EmptyContent(
                imageId = R.drawable.ic_magic_box,
                title = stringResource(R.string.watch_list_no_movies_title),
                subtitle = stringResource(id = R.string.find_your_movie_by),
                modifier = Modifier.fillMaxWidth(.5f),
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
            uiState = WatchListUiState.Loading,
            onSearchTextChange = { }
        )
    }
}