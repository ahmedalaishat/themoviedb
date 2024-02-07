package com.alaishat.ahmed.themoviedb.ui.feature.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.alaishat.ahmed.themoviedb.presentation.architecture.HomeTab
import com.alaishat.ahmed.themoviedb.presentation.common.model.Movie
import com.alaishat.ahmed.themoviedb.presentation.feature.home.HomeViewModel
import com.alaishat.ahmed.themoviedb.presentation.feature.home.model.HomeUiState
import com.alaishat.ahmed.themoviedb.presentation.feature.home.model.MovieListUiState
import com.alaishat.ahmed.themoviedb.ui.R
import com.alaishat.ahmed.themoviedb.ui.common.EmptyContent
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
import com.alaishat.ahmed.themoviedb.ui.theme.Dimensions.ScreenPadding
import com.alaishat.ahmed.themoviedb.ui.theme.Shapes
import eu.bambooapps.material3.pullrefresh.PullRefreshIndicator
import eu.bambooapps.material3.pullrefresh.PullRefreshIndicatorDefaults
import eu.bambooapps.material3.pullrefresh.pullRefresh
import eu.bambooapps.material3.pullrefresh.rememberPullRefreshState
import kotlinx.coroutines.flow.flowOf

/**
 * Created by Ahmed Al-Aishat on Jun/16/2023.
 * The Movie DB Project.
 */
const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w220_and_h330_face/"
const val BACKDROP_BASE_URL = "https://www.themoviedb.org/t/p/w1920_and_h800_multi_faces/"
const val AVATAR_BASE_URL = "https://www.themoviedb.org/t/p/w300_and_h300_bestv2/"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeRoute(
    onMovieClick: (movieId: Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val refreshableUiState by viewModel.refreshableUiState.collectAsStateWithLifecycle()

    val scrollState = rememberScrollState()
    val pullRefreshState = rememberPullRefreshState(refreshableUiState.isRefreshing, viewModel::refresh)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState),
    ) {
        when (refreshableUiState.uiState.topMoviesState) {
            is MovieListUiState.Error, MovieListUiState.NoCache ->
                ConnectionErrorContent(
                    onRetryClick = viewModel::refresh,
                )

            is MovieListUiState.Success, MovieListUiState.Loading ->
                HomeScreen(
                    scrollState = scrollState,
                    uiState = refreshableUiState.uiState,
                    onMovieClick = onMovieClick,
                )
        }
        PullRefreshIndicator(
            refreshing = refreshableUiState.isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
            colors = PullRefreshIndicatorDefaults.colors(
                contentColor = MaterialTheme.colorScheme.primary,
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        )
    }

    LaunchedEffect(refreshableUiState.isRefreshing) {
        if (refreshableUiState.isRefreshing)
            scrollState.scrollTo(0)
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomeScreen(
    uiState: HomeUiState,
    scrollState: ScrollState,
    onMovieClick: (movieId: Int) -> Unit,
) {

    BoxWithConstraints {
        val screenHeight = maxHeight
        val screenWidth = with(LocalConfiguration.current) { screenWidthDp.dp }
        //AHMED_TODO: remove me
        var text by remember { mutableStateOf("") }
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
                .padding(Dimensions.ScreenPadding)
        ) {
            Text(
                text = stringResource(R.string.what_do_you_want_to_watch),
                style = MaterialTheme.typography.titleLarge
            )
            SpacerMd()
            SearchBar(
                searchText = text,
                placeholder = stringResource(id = R.string.search),
                onSearchTextChange = { text = it },
                modifier = Modifier.fillMaxWidth(),
            )
            SpacerMd()
            TopFiveMovies(
                topMovies = uiState.topMoviesState,
                onMovieClick = onMovieClick,
                modifier = Modifier.requiredWidth(screenWidth)
            )
            val homeTabs = remember { uiState.tabs.keys.toList() }
            AppHorizontalPager(
                tabs = homeTabs.map { it.tabName },
                outerScrollState = scrollState,
                modifier = Modifier
                    .height(screenHeight)
                    .padding(top = MarginMd),
            ) { page ->
                val tabPagingItems = uiState.tabs[homeTabs[page]]!!.collectAsLazyPagingItems()
                HomePageContent(
                    pagingItems = tabPagingItems,
                    onMovieClick = onMovieClick,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun TopFiveMovies(
    topMovies: MovieListUiState,
    onMovieClick: (movieId: Int) -> Unit,
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
                    onMovieClick = onMovieClick,
                    cardModifier = Modifier
                        .width(170.dp)
                        .height(240.dp)
                )

            is MovieListUiState.Error, MovieListUiState.NoCache -> {}
        }
    }
}

@Composable
fun HomePageContent(
    pagingItems: LazyPagingItems<Movie>,
    onMovieClick: (movieId: Int) -> Unit,
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
            onMovieClick = onMovieClick,
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

@Composable
private fun ConnectionErrorContent(
    onRetryClick: () -> Unit,
) {
    EmptyContent(
        modifier = Modifier
            .fillMaxSize()
            .padding(ScreenPadding),
        imageId = R.drawable.no_connection,
        title = stringResource(id = R.string.no_connection),
        subtitle = stringResource(id = R.string.connect_for_first_time_movie_list_description),
        actionButtonText = stringResource(id = R.string.retry),
        onActionButtonClick = onRetryClick,
    )
}

@DevicePreviews
@Composable
private fun HomeScreenPreview() {
    //AHMED_TODO: add preview parameters
    TheMoviePreviewSurface {
        HomeScreen(
            scrollState = rememberScrollState(),
            uiState = HomeUiState(
                topMoviesState = MovieListUiState.Loading,
                tabs = HomeTab.entries.associateWith { flowOf() }
            ),
            onMovieClick = { },
//            tabsMap = mapOf(),
        )
    }
}