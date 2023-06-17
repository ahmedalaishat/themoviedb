package com.alaishat.ahmed.themoviedb.feature.movie

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.alaishat.ahmed.themoviedb.R
import com.alaishat.ahmed.themoviedb.feature.home.MovieCard
import com.alaishat.ahmed.themoviedb.ui.common.MovieInfo
import com.alaishat.ahmed.themoviedb.ui.component.AppHorizontalPager
import com.alaishat.ahmed.themoviedb.ui.component.DevicePreviews
import com.alaishat.ahmed.themoviedb.ui.component.RowDivider
import com.alaishat.ahmed.themoviedb.ui.component.SpacerLg
import com.alaishat.ahmed.themoviedb.ui.component.SpacerMd
import com.alaishat.ahmed.themoviedb.ui.component.SpacerSm
import com.alaishat.ahmed.themoviedb.ui.component.TheMoviePreviewSurface
import com.alaishat.ahmed.themoviedb.ui.theme.Dimensions
import com.alaishat.ahmed.themoviedb.ui.theme.Shapes

/**
 * Created by Ahmed Al-Aishat on Jun/17/2023.
 * The Movie DB Project.
 */
@Composable
fun MovieScreen() {
    val scrollState = rememberScrollState()

    BoxWithConstraints {
        val screenHeight = maxHeight
        Column(
            modifier = Modifier.verticalScroll(scrollState),
        ) {
            Box(
                modifier = Modifier
                    .height(270.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.BottomStart
            ) {
                Image(
                    painter = painterResource(id = R.drawable.alt_movie_cover),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(bottom = 60.dp)
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
//                    .fillMaxWidth(),
                )
                Box(
                    modifier = Modifier
                        .align(BottomEnd)
                        .padding(bottom = 60.dp + Dimensions.MarginSm, end = Dimensions.MarginSm)
                        .height(IntrinsicSize.Min)
                        .width(IntrinsicSize.Min)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(Shapes.CornerSmall)
                            .background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f))
                    )
                    MovieInfo(
                        modifier = Modifier.padding(
                            vertical = Dimensions.MarginXSm,
                            horizontal = Dimensions.MarginXSm.times(2)
                        ),
                        iconId = R.drawable.ic_star,
                        text = "9.5",
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
                Row(
                    modifier = Modifier.padding(horizontal = Dimensions.ScreenPadding),
                    verticalAlignment = Alignment.Bottom
                ) {
                    MovieCard(
                        movieImageId = R.drawable.alt_movie_2,
                        modifier = Modifier
                            .height(120.dp)
                            .width(95.dp),
                    )
                    SpacerSm()
                    Text(text = "Spiderman No Way Home", maxLines = 2, minLines = 2)
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
                    text = "2021",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                RowDivider(modifier = Modifier.padding(horizontal = Dimensions.MarginSm))
                MovieInfo(
                    iconId = R.drawable.ic_clock,
                    text = "148 minutes",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                RowDivider(modifier = Modifier.padding(horizontal = Dimensions.MarginSm))
                MovieInfo(
                    iconId = R.drawable.ic_ticket,
                    text = "Action",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            SpacerLg()
            //AHMED_TODO: convert me to enums
            val tabs = remember { listOf("About Movie", "Reviews", "Cast") }
            val actors = remember { listOf(Actor(1), Actor(2), Actor(3), Actor(4), Actor(5)) }

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
//                    0 -> CastTab(actors = actors, modifier = tabModifier)
                    0 -> AboutMovieTab(modifier = tabModifier)
                    1 -> ReviewsTab(modifier = tabModifier)
                    2 -> CastTab(actors = actors, modifier = tabModifier)
                }
            }
        }
    }
}

@Composable
private fun AboutMovieTab(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Text(text = "From DC Comics comes the Suicide Squad, an antihero team of incarcerated supervillains who act as deniable assets for the United States government, undertaking high-risk black ops missions in exchange for commuted prison sentences.")
    }
}

@Composable
private fun ReviewsTab(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Text(text = "From DC Comics comes the Suicide Squad, an antihero team of incarcerated supervillains who act as deniable assets for the United States government, undertaking high-risk black ops missions in exchange for commuted prison sentences.")
    }
}

@Composable
private fun CastTab(
    actors: List<Actor>,
    modifier: Modifier = Modifier
) {
    val lazyGridState = rememberLazyGridState()

    LazyVerticalGrid(
        state = lazyGridState,
        columns = GridCells.Adaptive(150.dp),
        horizontalArrangement = Arrangement.spacedBy(Dimensions.MarginSm),
        verticalArrangement = Arrangement.spacedBy(Dimensions.MarginSm),
        contentPadding = PaddingValues(vertical = Dimensions.MarginMd),
        modifier = modifier.fillMaxWidth(),
    ) {
        actors(
            actors = actors,
            actorModifier = Modifier
                .fillMaxWidth()
                .aspectRatio(.9f)
        )
    }
}


private fun LazyGridScope.actors(
    actors: List<Actor>,
    actorModifier: Modifier = Modifier,
) {
    items(items = actors, key = Actor::id) { actor ->
        ActorCard(actor = actor, modifier = actorModifier)
    }
}


@Composable
fun ActorCard(
    actor: Actor,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
    ) {
        Image(
            modifier = Modifier
                .align(TopCenter)
                .fillMaxWidth(.8f)
                .aspectRatio(1f),
            painter = painterResource(id = actor.imageId),
            contentDescription = actor.name
        )
        Text(
            modifier = Modifier.align(BottomCenter),
            text = actor.name, maxLines = 2, minLines = 2
        )
    }
}

data class Actor(
    val id: Int,
    val name: String = "Tom Holland",
    @DrawableRes val imageId: Int = R.drawable.alt_actor,
)


@DevicePreviews
@Composable
fun MovieScreenPreview() {
    TheMoviePreviewSurface {
        MovieScreen()
    }
}