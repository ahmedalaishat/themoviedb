package com.alaishat.ahmed.themoviedb.feature.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.alaishat.ahmed.themoviedb.R
import com.alaishat.ahmed.themoviedb.common.HomeTab
import com.alaishat.ahmed.themoviedb.domain.model.Movie
import com.alaishat.ahmed.themoviedb.ui.component.AppHorizontalPager
import com.alaishat.ahmed.themoviedb.ui.component.DevicePreviews
import com.alaishat.ahmed.themoviedb.ui.component.SearchBar
import com.alaishat.ahmed.themoviedb.ui.component.SpacerMd
import com.alaishat.ahmed.themoviedb.ui.component.TheMoviePreviewSurface
import com.alaishat.ahmed.themoviedb.ui.theme.Dimensions
import com.alaishat.ahmed.themoviedb.ui.theme.Dimensions.IconXLg
import com.alaishat.ahmed.themoviedb.ui.theme.Dimensions.MarginMd
import com.alaishat.ahmed.themoviedb.ui.theme.Dimensions.MarginSm
import com.alaishat.ahmed.themoviedb.ui.theme.Shapes
import kotlinx.coroutines.flow.StateFlow

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
    tabsMap: Map<HomeTab, StateFlow<MovieListUiState>>,
) {
    var text by remember { mutableStateOf("") }

    BoxWithConstraints {
        val screenHeight = maxHeight
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
                .padding(Dimensions.ScreenPadding)
        ) {
            Text(text = "What do you want to watch?")
            SpacerMd()
            SearchBar(
                searchText = text,
                placeholder = "Search",
                onSearchTextChange = { text = it },
                modifier = Modifier.fillMaxWidth(),
            )
            SpacerMd()
            TopFiveMovies(topFiveMovies)
            val homeTabs = remember { tabsMap.keys.toList() }

            AppHorizontalPager(
                tabs = homeTabs.map { it.tabName },
                outerScrollState = scrollState,
                modifier = Modifier
                    .height(screenHeight)
                    .padding(top = MarginMd),
            ) { page ->
                val tabState by tabsMap[homeTabs[page]]!!.collectAsStateWithLifecycle()
                HomePageContent(
                    tabState = tabState,
                )
            }
        }
    }
}

@Composable
private fun TopFiveMovies(
    topFiveMovies: MovieListUiState
) {
    when (topFiveMovies) {
        MovieListUiState.Loading -> {
            //AHMED_TODO: add shimmer
        }

        is MovieListUiState.Error -> {}
        is MovieListUiState.Success -> {
            val screenWidth = with(LocalConfiguration.current) { screenWidthDp.dp }

            LazyRow(
                modifier = Modifier.requiredWidth(screenWidth),
                horizontalArrangement = Arrangement.spacedBy(MarginSm),
                contentPadding = PaddingValues(horizontal = Dimensions.ScreenPadding)
            ) {
                itemsIndexed(topFiveMovies.movies, key = { _, item -> item.id }) { index, movie ->
                    TopMovieCard(
                        movie = movie,
                        rank = index + 1,
                        modifier = Modifier
                            .width(170.dp)
                            .height(240.dp),
                    )
                }
            }
        }
    }
}

@Composable
fun TopMovieCard(
    movie: Movie,
    rank: Int,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        MovieCard(
            movie = movie,
            modifier = Modifier
                .padding(start = MarginSm, end = MarginSm, bottom = MarginSm.times(2))
                .fillMaxSize(),
        )
        Image(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .height(IconXLg),
            painter = painterResource(id = getDrawableByRank(rank)),
            contentDescription = "rank"
        )
    }
}

@DrawableRes
private fun getDrawableByRank(rank: Int): Int {
    return when (rank) {
        1 -> R.drawable.number_1
        2 -> R.drawable.number_2
        3 -> R.drawable.number_3
        4 -> R.drawable.number_4
        else -> R.drawable.number_5
    }
}

//AHMED_TODO: remove me
@Composable
fun MovieCard(
    @DrawableRes movieImageId: Int,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = Shapes.CornerLarge
    ) {
        Image(
            painter = painterResource(id = movieImageId),
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun MovieCard(
    movie: Movie,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = Shapes.CornerLarge
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("$POSTER_BASE_URL${movie.posterPath}")
                .crossfade(true)
                .build(),
//            placeholder = painterResource(R.drawable.alt_movie_1),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun HomePageContent(
    modifier: Modifier = Modifier,
    tabState: MovieListUiState,
) {
    when (tabState) {
        is MovieListUiState.Loading ->
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(modifier = Modifier.size(50.dp))
            }

        is MovieListUiState.Error -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = tabState.message.asString(),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        is MovieListUiState.Success -> {
            val lazyGridState = rememberLazyGridState()

            LazyVerticalGrid(
                state = lazyGridState,
                columns = GridCells.Adaptive(100.dp),
                horizontalArrangement = Arrangement.spacedBy(MarginSm),
                verticalArrangement = Arrangement.spacedBy(MarginSm),
                contentPadding = PaddingValues(vertical = MarginMd),
                modifier = modifier.fillMaxWidth(),

                ) {
                items(tabState.movies, key = Movie::id) { movie ->
//            BoxWithConstraints {
                    MovieCard(
                        movie = movie,
                        modifier = Modifier
//                        .requiredWidthIn(max = min(maxWidth, 150.dp), min = 0.dp)
                            .aspectRatio(2 / 3f)
//                        .align(Alignment.Center),
                    )
//            }
                }
            }
        }
    }
}

@DevicePreviews
@Composable
private fun HomeScreenPreview() {
    TheMoviePreviewSurface {
        HomeScreen(
            topFiveMovies = MovieListUiState.Loading,
            tabsMap = mapOf(),
        )
    }
}