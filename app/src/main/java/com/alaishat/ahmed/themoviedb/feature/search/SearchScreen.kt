package com.alaishat.ahmed.themoviedb.feature.search

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
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
fun SearchRoute(
    viewModel: SearchViewModel = hiltViewModel()
) {
    val query by viewModel.queryFlow.collectAsStateWithLifecycle()
    val uiState by viewModel.searchMoviesFlow.collectAsStateWithLifecycle()

    SearchScreen(
        searchText = query,
        uiState = uiState,
        onSearchTextChange = viewModel::updateQueryText
    )
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SearchScreen(
    searchText: String,
    uiState: SearchUiState,
    onSearchTextChange: (String) -> Unit,
) {
    val searchFocusRequester = remember { FocusRequester() }
    val softwareKeyboardController = LocalSoftwareKeyboardController.current

    if (uiState == SearchUiState.Loading) CircularProgressIndicator()
    LazyVerticalGrid(
        verticalArrangement = Arrangement.spacedBy(Dimensions.MarginLg),
        contentPadding = PaddingValues(
            start = Dimensions.ScreenPadding,
            end = Dimensions.ScreenPadding,
            bottom = Dimensions.ScreenPadding,
        ),
        columns = GridCells.Adaptive(300.dp)
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            SearchBar(
                searchText = searchText,
                placeholder = "Search",
                onSearchTextChange = onSearchTextChange,
                modifier = Modifier
                    .padding(top = Dimensions.ScreenPadding)
                    .fillMaxWidth()
                    .focusRequester(searchFocusRequester)
            )
        }
        if (uiState is SearchUiState.Success)
            movieList(
                movies = uiState.movies,
                itemModifier = Modifier.height(110.dp),
            )
    }

    if (uiState == SearchUiState.NoResults || uiState == SearchUiState.Initial || uiState is SearchUiState.Error) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            val titleId = if (uiState == SearchUiState.Initial) R.string.find_your_movie_by
            else R.string.can_not_find_movie_title

            val subtitleId = if (uiState == SearchUiState.Initial) null
            else R.string.find_your_movie_by

            val actionButtonTextId = if (uiState == SearchUiState.Initial) R.string.search
            else null

            EmptyContent(
                imageId = R.drawable.ic_no_results,
                title = stringResource(titleId),
                subtitle = subtitleId?.let { stringResource(it) },
                modifier = Modifier.fillMaxWidth(.5f),
                actionButtonText = actionButtonTextId?.let { stringResource(it) },
                onActionButtonClick = {
                    searchFocusRequester.requestFocus()
                    softwareKeyboardController?.show()
                }
            )
        }
    }
}

@DevicePreviews
@Composable
private fun SearchScreenPreview() {
    TheMoviePreviewSurface {
        SearchScreen(
            searchText = "",
            uiState = SearchUiState.Initial,
            onSearchTextChange = { }
        )
    }
}