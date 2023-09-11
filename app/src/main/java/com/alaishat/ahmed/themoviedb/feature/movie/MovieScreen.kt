package com.alaishat.ahmed.themoviedb.feature.movie

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.alaishat.ahmed.themoviedb.R
import com.alaishat.ahmed.themoviedb.domain.model.Credit
import com.alaishat.ahmed.themoviedb.domain.model.MovieDetails
import com.alaishat.ahmed.themoviedb.domain.model.Review
import com.alaishat.ahmed.themoviedb.feature.home.AVATAR_BASE_URL
import com.alaishat.ahmed.themoviedb.feature.home.BACKDROP_BASE_URL
import com.alaishat.ahmed.themoviedb.feature.rate.RateBottomSheet
import com.alaishat.ahmed.themoviedb.ui.common.MovieCard
import com.alaishat.ahmed.themoviedb.ui.common.MovieInfo
import com.alaishat.ahmed.themoviedb.ui.common.TheMovieLoader
import com.alaishat.ahmed.themoviedb.ui.common.imageRequest
import com.alaishat.ahmed.themoviedb.ui.component.AppHorizontalPager
import com.alaishat.ahmed.themoviedb.ui.component.DevicePreviews
import com.alaishat.ahmed.themoviedb.ui.component.ExpandingText
import com.alaishat.ahmed.themoviedb.ui.component.RowDivider
import com.alaishat.ahmed.themoviedb.ui.component.SpacerLg
import com.alaishat.ahmed.themoviedb.ui.component.SpacerMd
import com.alaishat.ahmed.themoviedb.ui.component.SpacerSm
import com.alaishat.ahmed.themoviedb.ui.component.SuccessDialog
import com.alaishat.ahmed.themoviedb.ui.component.TheMoviePreviewSurface
import com.alaishat.ahmed.themoviedb.ui.component.rememberDialogState
import com.alaishat.ahmed.themoviedb.ui.extenstions.darker
import com.alaishat.ahmed.themoviedb.ui.extenstions.pagingInitialLoader
import com.alaishat.ahmed.themoviedb.ui.extenstions.pagingLoader
import com.alaishat.ahmed.themoviedb.ui.theme.AppRed
import com.alaishat.ahmed.themoviedb.ui.theme.Dimensions
import com.alaishat.ahmed.themoviedb.ui.theme.Shapes
import com.alaishat.ahmed.themoviedb.ui.theme.Shapes.CornerFull

/**
 * Created by Ahmed Al-Aishat on Jun/17/2023.
 * The Movie DB Project.
 */
@Composable
fun MovieRoute(
    viewModel: MovieViewModel = hiltViewModel(),
) {
    val movie by viewModel.movieDetails.collectAsStateWithLifecycle()
    val reviews = viewModel.movieReviews.collectAsLazyPagingItems()
    val credits by viewModel.movieCredits.collectAsStateWithLifecycle()
    val rated by viewModel.rated.collectAsStateWithLifecycle()

    //AHMED_TODO: make me shimmer
    if (movie == null) TheMovieLoader()
    else MovieScreen(
        movie = movie!!,
        reviews = reviews,
        credits = credits,
        rated = rated,
        onRateSubmit = viewModel::rateMovie,
        onToggleWatchlist = viewModel::toggleWatchlist,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MovieScreen(
    movie: MovieDetails,
    reviews: LazyPagingItems<Review>,
    credits: List<Credit>?,
    rated: Boolean,
    onRateSubmit: (rating: Int) -> Unit,
    onToggleWatchlist: (watchlist: Boolean) -> Unit,
) {
    val scrollState = rememberScrollState()


    var showSheet by remember { mutableStateOf(false) }

    if (showSheet) {
        RateBottomSheet(
            onRateSubmit = onRateSubmit,
            onDismiss = {
                showSheet = false
            },
        )
    }

    val successDialogState = rememberDialogState()
    SuccessDialog(
        dialogState = successDialogState,
        message = stringResource(id = R.string.rating_success_message),
        onPositiveClick = { }
    )

    LaunchedEffect(rated) {
        if (rated) {
            successDialogState.show()
            showSheet = false
        }
    }

    BoxWithConstraints {
        val screenHeight = maxHeight
        Column(
            modifier = Modifier.verticalScroll(scrollState),
        ) {
            Box(
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.BottomStart
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("$BACKDROP_BASE_URL${movie.backdropPath}")
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(bottom = 75.dp)
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .align(BottomEnd)
                        .padding(bottom = 75.dp + Dimensions.MarginSm, end = Dimensions.MarginSm)
                        .height(IntrinsicSize.Min)
                        .width(IntrinsicSize.Min)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(Shapes.CornerSmall)
                            .background(
                                MaterialTheme.colorScheme.onSurfaceVariant
                                    .darker(0.4f)
                                    .copy(alpha = 0.6f)
                            )
                    )
                    MovieInfo(
                        modifier = Modifier.padding(
                            vertical = Dimensions.MarginXSm,
                            horizontal = Dimensions.MarginXSm.times(2)
                        ),
                        iconId = R.drawable.ic_star,
                        text = movie.voteAverage,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
                Icon(
                    modifier = Modifier
                        .padding(top = Dimensions.MarginSm, end = Dimensions.MarginSm)
                        .align(TopEnd)
                        .alpha(.9f)
                        .clickable {
                            onToggleWatchlist(movie.watchlist.not())
                        },
                    painter = painterResource(id = R.drawable.ic_bookmark),
                    contentDescription = null,
                    tint = if (movie.watchlist) AppRed else Color.Unspecified
                )
                Row(
                    modifier = Modifier.padding(horizontal = Dimensions.ScreenPadding),
                    verticalAlignment = Alignment.Bottom
                ) {
                    MovieCard(
                        moviePosterPath = movie.posterPath,
                        modifier = Modifier
                            .height(150.dp)
                            .width(100.dp),
                    )
                    SpacerSm()
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(75.dp),
                        contentAlignment = CenterStart
                    ) {
                        Text(
                            text = movie.title,
                            maxLines = 2,
                            style = MaterialTheme.typography.titleLarge,
                        )
                    }
                }
            }
            SpacerMd()
            Row(
                modifier = Modifier
                    .align(CenterHorizontally)
                    .height(IntrinsicSize.Min)
            ) {
                MovieInfo(
                    iconId = R.drawable.ic_calendar,
                    text = movie.releaseYear,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    iconSize = 20.dp,
                )
                RowDivider(modifier = Modifier.padding(horizontal = Dimensions.MarginSm))
                MovieInfo(
                    iconId = R.drawable.ic_clock,
                    text = movie.runtime,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    iconSize = 20.dp,
                )
                RowDivider(modifier = Modifier.padding(horizontal = Dimensions.MarginSm))
                if (movie.genre != null)
                    MovieInfo(
                        iconId = R.drawable.ic_ticket,
                        text = movie.genre,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        iconSize = 20.dp,
                    )
            }
            SpacerMd()
            //AHMED_TODO: convert me to enums
            val tabs = remember { listOf("About Movie", "Reviews", "Cast") }

            AppHorizontalPager(
                tabs = tabs,
                modifier = Modifier
                    .height(screenHeight)
                    .padding(horizontal = Dimensions.ScreenPadding),
                outerScrollState = scrollState,
            ) { page ->
                val tabModifier = Modifier
                    .padding(vertical = Dimensions.MarginMd)
                    .fillMaxSize()
                when (page) {
                    0 -> AboutMovieTab(
                        movieOverview = movie.overview,
                        rated = rated,
                        onRateClick = { showSheet = true },
                        modifier = tabModifier,
                    )

                    1 -> ReviewsTab(
                        pagingReviews = reviews,
                        modifier = tabModifier,
                    )

                    2 -> CastTab(
                        credits = credits,
                        modifier = tabModifier,
                    )
                }
            }
        }
    }
}

@Composable
private fun AboutMovieTab(
    movieOverview: String,
    rated: Boolean,
    onRateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = CenterHorizontally
    ) {
        Text(text = movieOverview)
        SpacerLg()
        if (!rated)
            OutlinedButton(
                onClick = onRateClick,
                modifier = Modifier.fillMaxWidth(.5f),
            ) {
                Text(text = stringResource(R.string.rate_this_movie))
            }
    }
}

@Composable
private fun ReviewsTab(
    pagingReviews: LazyPagingItems<Review>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        state = rememberLazyListState(),
        verticalArrangement = Arrangement.spacedBy(Dimensions.MarginLg),
        contentPadding = PaddingValues(vertical = Dimensions.MarginMd),
        modifier = modifier.fillMaxWidth(),
    ) {
        val reviewModifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()

        //AHMED_TODO: make me shimmer
        pagingInitialLoader(pagingReviews.loadState) {
            item {
                TheMovieLoader()
            }
        }

        reviews(
            reviews = pagingReviews,
            reviewModifier = reviewModifier
        )


        pagingLoader(pagingReviews.loadState) {
            item {
                TheMovieLoader()
            }
        }
    }
}

@Composable
private fun CastTab(
    credits: List<Credit>?,
    modifier: Modifier = Modifier
) {
    val lazyGridState = rememberLazyGridState()

    //AHMED_TODO: make me shimmer
    if (credits == null) return TheMovieLoader()

    LazyVerticalGrid(
        state = lazyGridState,
        columns = GridCells.Adaptive(150.dp),
        horizontalArrangement = Arrangement.spacedBy(Dimensions.MarginSm),
        verticalArrangement = Arrangement.spacedBy(Dimensions.MarginSm),
        contentPadding = PaddingValues(vertical = Dimensions.MarginMd),
        modifier = modifier.fillMaxWidth(),
    ) {
        actors(
            credits = credits,
            actorModifier = Modifier
                .fillMaxWidth()
                .aspectRatio(.9f)
        )
    }
}


private fun LazyGridScope.actors(
    credits: List<Credit>,
    actorModifier: Modifier = Modifier,
) {
    items(items = credits, key = Credit::id) { credit ->
        ActorCard(credit = credit, modifier = actorModifier)
    }
}


@Composable
fun ActorCard(
    credit: Credit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
    ) {
        AsyncImage(
            modifier = Modifier
                .align(TopCenter)
                .fillMaxWidth(.8f)
                .aspectRatio(1f)
                .clip(CornerFull),
            model = imageRequest(
                data = credit.profilePath?.let { "$AVATAR_BASE_URL${it}" }
                    ?: R.drawable.alt_avatar
            ),
            contentDescription = credit.name,
        )
        Text(
            modifier = Modifier.align(BottomCenter),
            text = credit.name,
            maxLines = 2,
            minLines = 2,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleSmall,
        )
    }
}


private fun LazyListScope.reviews(
    reviews: LazyPagingItems<Review>,
    reviewModifier: Modifier = Modifier,
) = items(
    count = reviews.itemCount,
    key = reviews.itemKey(Review::id),
    itemContent = { index ->
        ReviewCard(review = reviews[index]!!, modifier = reviewModifier)
    },
)


@Composable
fun ReviewCard(
    review: Review,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
    ) {
        Column(
            horizontalAlignment = CenterHorizontally,
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CornerFull),
                model = imageRequest(
                    data = review.authorAvatarPath?.let { "$AVATAR_BASE_URL${it}" }
                        ?: R.drawable.alt_avatar
                ),
                contentDescription = review.authorName,
            )
            SpacerMd()
            if (review.rating != null)
                Text(
                    text = review.rating,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelMedium
                )
        }
        SpacerSm()
        Column {
            Text(text = review.authorName, style = MaterialTheme.typography.titleSmall)
            SpacerSm()
            ExpandingText(text = review.content)
        }
    }
}

@DevicePreviews
@Composable
fun MovieScreenPreview() {
    TheMoviePreviewSurface {
//        MovieScreen(Movie()        )
    }
}