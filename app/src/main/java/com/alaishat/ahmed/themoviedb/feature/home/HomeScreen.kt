package com.alaishat.ahmed.themoviedb.feature.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.alaishat.ahmed.themoviedb.R
import com.alaishat.ahmed.themoviedb.ui.component.DevicePreviews
import com.alaishat.ahmed.themoviedb.ui.component.SearchBar
import com.alaishat.ahmed.themoviedb.ui.component.SpacerMd
import com.alaishat.ahmed.themoviedb.ui.component.TheMoviePreviewSurface
import com.alaishat.ahmed.themoviedb.ui.extenstions.verticalNestedScroll
import com.alaishat.ahmed.themoviedb.ui.theme.Dimensions
import com.alaishat.ahmed.themoviedb.ui.theme.Dimensions.IconXLg
import com.alaishat.ahmed.themoviedb.ui.theme.Dimensions.MarginMd
import com.alaishat.ahmed.themoviedb.ui.theme.Dimensions.MarginSm
import com.alaishat.ahmed.themoviedb.ui.theme.Shapes
import kotlinx.coroutines.launch

/**
 * Created by Ahmed Al-Aishat on Jun/16/2023.
 * The Movie DB Project.
 */
@Composable
fun HomeScreen() {
    var text by remember { mutableStateOf("") }

    BoxWithConstraints {
        val screenHeight = maxHeight
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
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
            TopFiveMovies()
            val tabs = remember { listOf("Now playing", "Upcoming", "Top rated", "Popular") }
            HomePager(
                tabs = tabs,
                outerScrollState = scrollState,
                modifier = Modifier.height(screenHeight),
            )
        }
    }
}

@Composable
private fun TopFiveMovies() {
    val screenWidth = with(LocalConfiguration.current) { screenWidthDp.dp }

    LazyRow(
        modifier = Modifier.requiredWidth(screenWidth),
        horizontalArrangement = Arrangement.spacedBy(MarginSm),
        contentPadding = PaddingValues(horizontal = Dimensions.ScreenPadding)
    ) {
        items(5) {
            TopMovieCard(
                rank = it + 1,
                modifier = Modifier
                    .width(170.dp)
                    .height(240.dp),
            )
        }
    }
}

@Composable
fun TopMovieCard(
    rank: Int,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        MovieCard(
            movieImageId = if (rank % 2 == 1) R.drawable.alt_movie_1 else R.drawable.alt_movie_2,
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomePager(
    tabs: List<String>,
    modifier: Modifier = Modifier,
    outerScrollState: ScrollState? = null,
) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = modifier) {
        SpacerMd()
        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = MaterialTheme.colorScheme.background,
            edgePadding = 0.dp,
            divider = { Divider(color = MaterialTheme.colorScheme.background) },
        ) {
            tabs.forEachIndexed { position, tab ->
                Tab(
                    text = {
                        Text(
                            text = tab,
                            style = MaterialTheme.typography.labelSmall
                        )
                    },
                    selected = pagerState.currentPage == position,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.scrollToPage(position)
                        }
                    },
                    unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        val pagerModifier = if (outerScrollState == null) Modifier
        else Modifier.verticalNestedScroll(outerScrollState)

        HorizontalPager(
            pageCount = tabs.size,
            state = pagerState,
            userScrollEnabled = false,
            modifier = pagerModifier,
        ) { position ->
            HomePageContent()
        }
    }
}

@Composable
fun HomePageContent(
    modifier: Modifier = Modifier,
) {
    val lazyGridState = rememberLazyGridState()

    LazyVerticalGrid(
        state = lazyGridState,
        columns = GridCells.Adaptive(100.dp),
        horizontalArrangement = Arrangement.spacedBy(MarginSm),
        verticalArrangement = Arrangement.spacedBy(MarginSm),
        contentPadding = PaddingValues(vertical = MarginMd),
        modifier = modifier.fillMaxWidth(),

        ) {
        items(10) {
//            BoxWithConstraints {
            MovieCard(
                movieImageId = if (it % 2 == 1) R.drawable.alt_movie_1 else R.drawable.alt_movie_2,
                modifier = Modifier
//                        .requiredWidthIn(max = min(maxWidth, 150.dp), min = 0.dp)
                    .aspectRatio(2 / 3f)
//                        .align(Alignment.Center),
            )
//            }
        }
    }
}

@DevicePreviews
@Composable
private fun HomeScreenPreview() {
    TheMoviePreviewSurface {
        HomeScreen()
    }
}