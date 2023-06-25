package com.alaishat.ahmed.themoviedb.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.alaishat.ahmed.themoviedb.R
import com.alaishat.ahmed.themoviedb.domain.model.Movie
import com.alaishat.ahmed.themoviedb.feature.home.MovieCard
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
            movie = movie,
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(.8f, true),
        )
        SpacerSm()
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = movie.title, maxLines = 1)
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