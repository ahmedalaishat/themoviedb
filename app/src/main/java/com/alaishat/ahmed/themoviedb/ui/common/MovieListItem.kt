package com.alaishat.ahmed.themoviedb.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alaishat.ahmed.themoviedb.R
import com.alaishat.ahmed.themoviedb.presentation.common.model.Movie
import com.alaishat.ahmed.themoviedb.ui.component.SpacerSm

/**
 * Created by Ahmed Al-Aishat on Jun/17/2023.
 * The Movie DB Project.
 */
@Composable
fun MovieListItem(
    movie: Movie,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        MovieCard(
            moviePosterPath = movie.posterPath,
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(.8f, true),
        )
        SpacerSm()
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = movie.title,
                maxLines = 1,
                style = MaterialTheme.typography.titleMedium,
            )
            Column {
                MovieInfo(
                    iconId = R.drawable.ic_star,
                    text = movie.voteAverage,
                    color = MaterialTheme.colorScheme.secondary
                )
                MovieInfo(iconId = R.drawable.ic_ticket, text = "Action")
                MovieInfo(iconId = R.drawable.ic_calendar, text = movie.releaseDate)
//                MovieInfo(iconId = R.drawable.ic_clock, text = "139 minutes")
            }
        }
    }
}

@Composable
fun MovieListItemShimmer(
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        ShimmerCard(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(.8f, true),
        )
        SpacerSm()
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            ShimmerCard(
                modifier = Modifier
                    .height(16.dp)
                    .fillMaxWidth(.6f)
            )
            Column {
                ShimmerCard(
                    modifier = Modifier
                        .padding(4.dp)
                        .height(16.dp)
                        .fillMaxWidth(.3f),
                )
                ShimmerCard(
                    modifier = Modifier
                        .padding(4.dp)
                        .height(16.dp)
                        .fillMaxWidth(.5f),
                )
                ShimmerCard(
                    modifier = Modifier
                        .padding(4.dp)
                        .height(16.dp)
                        .fillMaxWidth(.4f),
                )
            }
        }
    }
}