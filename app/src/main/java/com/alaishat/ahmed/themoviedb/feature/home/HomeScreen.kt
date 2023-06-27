package com.alaishat.ahmed.themoviedb.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.alaishat.ahmed.themoviedb.R
import com.alaishat.ahmed.themoviedb.common.HomeTab
import com.alaishat.ahmed.themoviedb.domain.model.Movie
import com.alaishat.ahmed.themoviedb.ui.common.ShimmerCard
import com.alaishat.ahmed.themoviedb.ui.common.movieCardsList
import com.alaishat.ahmed.themoviedb.ui.common.topMoviesList
import com.alaishat.ahmed.themoviedb.ui.component.AppHorizontalPager
import com.alaishat.ahmed.themoviedb.ui.component.DevicePreviews
import com.alaishat.ahmed.themoviedb.ui.component.SearchBar
import com.alaishat.ahmed.themoviedb.ui.component.SpacerMd
import com.alaishat.ahmed.themoviedb.ui.component.TheMoviePreviewSurface
import com.alaishat.ahmed.themoviedb.ui.extenstions.PagingErrorBox
import com.alaishat.ahmed.themoviedb.ui.extenstions.maxLineBox
import com.alaishat.ahmed.themoviedb.ui.extenstions.pagingInitialLoader
import com.alaishat.ahmed.themoviedb.ui.extenstions.pagingLoader
import com.alaishat.ahmed.themoviedb.ui.theme.Dimensions
import com.alaishat.ahmed.themoviedb.ui.theme.Dimensions.MarginMd
import com.alaishat.ahmed.themoviedb.ui.theme.Dimensions.MarginSm
import com.alaishat.ahmed.themoviedb.ui.theme.Shapes
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ahmed Al-Aishat on Jun/16/2023.
 * The Movie DB Project.
 */
const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w220_and_h330_face/"

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val topFiveMovies by viewModel.topFiveMoviesFlow.collectAsStateWithLifecycle()

    HomeScreen(
        topFiveMovies = topFiveMovies,
        tabsMap = viewModel.tabs,
    )
}


@Composable
private fun HomeScreen(
    topFiveMovies: MovieListUiState,
    tabsMap: Map<HomeTab, Flow<PagingData<Movie>>>,
) {
    BoxWithConstraints {
        val screenHeight = maxHeight
        val screenWidth = with(LocalConfiguration.current) { screenWidthDp.dp }
        //AHMED_TODO: remove me
        var text by remember { mutableStateOf("") }
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
                .padding(Dimensions.ScreenPadding)
        ) {
            Text(text = stringResource(R.string.what_do_you_want_to_watch))
            SpacerMd()
            SearchBar(
                searchText = text,
                placeholder = stringResource(id = R.string.search),
                onSearchTextChange = { text = it },
                modifier = Modifier.fillMaxWidth(),
            )
            SpacerMd()
            TopFiveMovies(
                topMovies = topFiveMovies,
                modifier = Modifier.requiredWidth(screenWidth)
            )

            val homeTabs = remember { tabsMap.keys.toList() }
            AppHorizontalPager(
                tabs = homeTabs.map { it.tabName },
                outerScrollState = scrollState,
                modifier = Modifier
                    .height(screenHeight)
                    .padding(top = MarginMd),
            ) { page ->
                val tabPagingItems = tabsMap[homeTabs[page]]!!.collectAsLazyPagingItems()
                HomePageContent(
                    pagingItems = tabPagingItems,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun TopFiveMovies(
    topMovies: MovieListUiState,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(MarginSm),
        contentPadding = PaddingValues(horizontal = Dimensions.ScreenPadding)
    ) {
        when (topMovies) {
            MovieListUiState.Loading -> {
                items(5) {
                    ShimmerCard(
                        modifier = Modifier
                            .width(170.dp)
                            .height(240.dp),
                        shape = Shapes.CornerLarge
                    )
                }
            }

            is MovieListUiState.Success ->
                topMoviesList(
                    topMovies = topMovies,
                    cardModifier = Modifier
                        .width(170.dp)
                        .height(240.dp)
                )

            is MovieListUiState.Error -> {}
        }
    }
}

@Composable
fun HomePageContent(
    pagingItems: LazyPagingItems<Movie>,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        state = rememberLazyGridState(),
        columns = GridCells.Adaptive(100.dp),
        horizontalArrangement = Arrangement.spacedBy(MarginSm),
        verticalArrangement = Arrangement.spacedBy(MarginSm),
        contentPadding = PaddingValues(vertical = MarginMd),
        modifier = modifier,
    ) {

        val movieCardModifier = Modifier.aspectRatio(2 / 3f)

        pagingInitialLoader(pagingItems.loadState) {
            // Shimmer
            items(count = 9) {
                ShimmerCard(
                    modifier = movieCardModifier,
                    shape = Shapes.CornerLarge
                )
            }
        }

        movieCardsList(
            pagingItems = pagingItems,
            cardModifier = movieCardModifier
        )

        pagingLoader(pagingItems.loadState) {
            maxLineBox(Modifier.fillMaxWidth()) {
                CircularProgressIndicator()
            }
        }
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
}

@DevicePreviews
@Composable
private fun HomeScreenPreview() {
    //AHMED_TODO: add preview parameters
    TheMoviePreviewSurface {
        HomeScreen(
            topFiveMovies = MovieListUiState.Loading,
            tabsMap = mapOf(),
        )
    }
}