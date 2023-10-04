package com.alaishat.ahmed.themoviedb.ui.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.alaishat.ahmed.themoviedb.R
import com.alaishat.ahmed.themoviedb.domain.model.MovieDomainModel
import com.alaishat.ahmed.themoviedb.ui.feature.home.POSTER_BASE_URL
import com.alaishat.ahmed.themoviedb.ui.theme.Dimensions
import com.alaishat.ahmed.themoviedb.ui.theme.Shapes

/**
 * Created by Ahmed Al-Aishat on Jun/27/2023.
 * The Movie DB Project.
 */
@Composable
fun MovieCard(
    moviePosterPath:String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = Shapes.CornerLarge
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("$POSTER_BASE_URL${moviePosterPath}")
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
fun TopMovieCard(
    movieDomainModel: MovieDomainModel,
    rank: Int,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        MovieCard(
            moviePosterPath = movieDomainModel.posterPath,
            modifier = Modifier
                .padding(start = Dimensions.MarginSm, end = Dimensions.MarginSm, bottom = Dimensions.MarginSm.times(2))
                .fillMaxSize(),
        )
        Image(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .height(Dimensions.IconXLg),
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