package com.alaishat.ahmed.themoviedb.feature.watchlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alaishat.ahmed.themoviedb.R
import com.alaishat.ahmed.themoviedb.ui.common.EmptyContent
import com.alaishat.ahmed.themoviedb.ui.common.movieList
import com.alaishat.ahmed.themoviedb.ui.component.DevicePreviews
import com.alaishat.ahmed.themoviedb.ui.component.SearchBar
import com.alaishat.ahmed.themoviedb.ui.component.TheMoviePreviewSurface
import com.alaishat.ahmed.themoviedb.ui.theme.Dimensions

/**
 * Created by Ahmed Al-Aishat on Jun/17/2023.
 * The Movie DB Project.
 */
@Composable
fun SearchScreen() {
    var searchText by remember { mutableStateOf("") }

    LazyVerticalGrid(
        verticalArrangement = Arrangement.spacedBy(Dimensions.MarginLg),
        contentPadding = PaddingValues(
            start = Dimensions.ScreenPadding,
            end = Dimensions.ScreenPadding,
            bottom = Dimensions.ScreenPadding,
        ),
        columns = GridCells.Adaptive(300.dp),
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
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
            movieList(itemModifier = Modifier.height(120.dp))
    }
    if (searchText.isNotEmpty())
        Box(
            modifier = Modifier.fillMaxSize(.5f),
            contentAlignment = Alignment.Center
        ) {
            EmptyContent(
                imageId = R.drawable.ic_magic_box,
                title = "There Is No Movie Yet!",
                text = "Find your movie by Type title, categories, years, etc ",
                modifier = Modifier.fillMaxWidth(.5f),
            )
        }
}

@DevicePreviews
@Composable
private fun SearchScreenPreview() {
    TheMoviePreviewSurface {
        SearchScreen()
    }
}