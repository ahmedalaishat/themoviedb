package com.alaishat.ahmed.themoviedb.ui.feature.search

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
import androidx.compose.material3.Text
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
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.alaishat.ahmed.themoviedb.R
import com.alaishat.ahmed.themoviedb.presentation.common.model.Movie
import com.alaishat.ahmed.themoviedb.presentation.feature.search.SearchViewModel
import com.alaishat.ahmed.themoviedb.ui.common.EmptyContent
import com.alaishat.ahmed.themoviedb.ui.common.MovieListItemShimmer
import com.alaishat.ahmed.themoviedb.ui.common.movieInfoList
import com.alaishat.ahmed.themoviedb.ui.component.DevicePreviews
import com.alaishat.ahmed.themoviedb.ui.component.SearchBar
import com.alaishat.ahmed.themoviedb.ui.component.TheMoviePreviewSurface
import com.alaishat.ahmed.themoviedb.ui.extenstions.PagingEmptyBox
import com.alaishat.ahmed.themoviedb.ui.extenstions.PagingErrorBox
import com.alaishat.ahmed.themoviedb.ui.extenstions.PagingInitialLoader
import com.alaishat.ahmed.themoviedb.ui.extenstions.maxLineBox
import com.alaishat.ahmed.themoviedb.ui.extenstions.pagingInitialLoader
import com.alaishat.ahmed.themoviedb.ui.extenstions.pagingLoader
import com.alaishat.ahmed.themoviedb.ui.theme.Dimensions
import kotlinx.coroutines.flow.flowOf

/**
 * Created by Ahmed Al-Aishat on Jun/17/2023.
 * The Movie DB Project.
 */
@Composable
fun SearchRoute(
    onMovieClick: (movieId: Int) -> Unit,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val query by viewModel.queryFlow.collectAsStateWithLifecycle()
    val pagingItems = viewModel.searchMoviesFlow.collectAsLazyPagingItems()

    SearchScreen(
        searchText = query,
        pagingItems = pagingItems,
        onSearchTextChange = viewModel::updateQueryText,
        onMovieClick = onMovieClick,
    )
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SearchScreen(
    searchText: String,
    pagingItems: LazyPagingItems<Movie>,
    onSearchTextChange: (String) -> Unit,
    onMovieClick: (movieId: Int) -> Unit,
) {
    val searchFocusRequester = remember { FocusRequester() }
    val softwareKeyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        PagingInitialLoader(pagingItems.loadState) {
            if (searchText.isEmpty()) EmptyContent(
                imageId = R.drawable.ic_no_results,
                title = stringResource(R.string.find_your_movie_by),
                subtitle = null,
                modifier = Modifier.fillMaxWidth(.5f),
                actionButtonText = stringResource(id = R.string.search),
                onActionButtonClick = {
                    searchFocusRequester.requestFocus()
                    softwareKeyboardController?.show()
                }
            )
//            else if (pagingItems.itemCount > 0) CircularProgressIndicator()
        }
    }

    PagingEmptyBox(
        pagingItems = pagingItems,
        modifier = Modifier.fillMaxSize(),
    ) {
        EmptyContent(
            imageId = R.drawable.ic_no_results,
            title = stringResource(R.string.can_not_find_movie_title),
            subtitle = stringResource(id = R.string.find_your_movie_by),
            modifier = Modifier.fillMaxWidth(.5f),
        )
    }

    PagingErrorBox(
        pagingItems = pagingItems,
        modifier = Modifier.fillMaxSize(),
    ) { error ->
        Text(
            text = error.orEmpty(),
            color = MaterialTheme.colorScheme.onSurfaceVariant
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

        val itemModifier = Modifier.height(110.dp)

        pagingInitialLoader(pagingItems.loadState) {
            if (/*pagingItems.itemCount == 0 &&*/ searchText.isNotEmpty())
                items(count = 10) { // Shimmer
                    MovieListItemShimmer(modifier = itemModifier)
                }
        }

        movieInfoList(
            pagingItems = pagingItems,
            onMovieClick = onMovieClick,
            itemModifier = itemModifier,
        )

        pagingLoader(pagingItems.loadState) {
            maxLineBox(Modifier.fillMaxWidth()) {
                CircularProgressIndicator()
            }
        }
    }
}

@DevicePreviews
@Composable
private fun SearchScreenPreview() {
    TheMoviePreviewSurface {
        SearchScreen(
            searchText = "",
            pagingItems = flowOf(PagingData.empty<Movie>()).collectAsLazyPagingItems(),
            onSearchTextChange = { },
        ) { }
    }
}