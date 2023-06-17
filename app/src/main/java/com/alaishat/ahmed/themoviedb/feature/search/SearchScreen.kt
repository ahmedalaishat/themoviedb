package com.alaishat.ahmed.themoviedb.feature.search

import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alaishat.ahmed.themoviedb.R
import com.alaishat.ahmed.themoviedb.feature.home.MovieCard
import com.alaishat.ahmed.themoviedb.ui.component.SearchBar
import com.alaishat.ahmed.themoviedb.ui.component.SpacerSm
import com.alaishat.ahmed.themoviedb.ui.component.SpacerXSm
import com.alaishat.ahmed.themoviedb.ui.component.TheMoviePreviewSurface
import com.alaishat.ahmed.themoviedb.ui.theme.Dimensions

/**
 * Created by Ahmed Al-Aishat on Jun/17/2023.
 * The Movie DB Project.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchScreen() {
    var searchText by remember { mutableStateOf("") }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(Dimensions.MarginLg),
        contentPadding = PaddingValues(
            start = Dimensions.ScreenPadding,
            end = Dimensions.ScreenPadding,
            bottom = Dimensions.ScreenPadding,
        )
    ) {
        stickyHeader {
            Surface(color = MaterialTheme.colorScheme.background) {
                SearchBar(
                    searchText = searchText,
                    placeholder = "Search",
                    onSearchTextChange = { searchText = it },
                    modifier = Modifier
                        .padding(top = Dimensions.ScreenPadding)
                        .fillMaxWidth()
                )
            }
        }
        if (searchText.isEmpty())
            items(10) {
                MovieListItem(it)
            }
    }
    if (searchText.isNotEmpty())
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            EmptySearchContent(
                modifier = Modifier.fillMaxWidth(.5f),
            )
        }
}

@Composable
private fun MovieListItem(index: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.height(120.dp),
    ) {
        MovieCard(
            movieImageId = if (index % 2 == 1) R.drawable.alt_movie_1 else R.drawable.alt_movie_2,
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(.8f, true),
        )
        SpacerSm()
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Spiderman", maxLines = 1)
            Column {
                MovieInfo(iconId = R.drawable.star, text = "9.5", color = MaterialTheme.colorScheme.secondary)
                MovieInfo(iconId = R.drawable.ticket, text = "Action")
                MovieInfo(iconId = R.drawable.ic_calendar, text = "2019")
                MovieInfo(iconId = R.drawable.ic_clock, text = "139 minutes")
            }
        }
    }
}

@Composable
fun MovieInfo(
    @DrawableRes iconId: Int,
    text: String,
    color: Color = Color.Unspecified,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            tint = color,
        )
        SpacerXSm()
        Text(text = text, color = color)
    }
}


@Composable
fun EmptySearchContent(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier.size(75.dp),
            painter = painterResource(id = R.drawable.ic_no_results),
            contentDescription = null
        )
        Text(
            text = "We Are Sorry, We Can Not Find The Movie :(",
            textAlign = TextAlign.Center,
        )
        Text(
            text = "Find your movie by Type title, categories, years, etc ",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun SearchScreenPreview() {
    TheMoviePreviewSurface {
        SearchScreen()
    }
}