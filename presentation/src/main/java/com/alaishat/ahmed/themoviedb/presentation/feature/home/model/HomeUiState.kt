package com.alaishat.ahmed.themoviedb.presentation.feature.home.model

import androidx.paging.PagingData
import com.alaishat.ahmed.themoviedb.presentation.architecture.HomeTab
import com.alaishat.ahmed.themoviedb.presentation.common.model.Movie
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ahmed Al-Aishat on Feb/01/2024.
 * The Movie DB Project.
 */
data class HomeUiState(
    val topMoviesState: MovieListUiState,
    val tabs: Map<HomeTab, Flow<PagingData<Movie>>>,
)