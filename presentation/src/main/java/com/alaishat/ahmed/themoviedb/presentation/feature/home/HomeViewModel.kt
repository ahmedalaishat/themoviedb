package com.alaishat.ahmed.themoviedb.presentation.feature.home

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.alaishat.ahmed.themoviedb.domain.usecase.GetMoviesPageUseCase
import com.alaishat.ahmed.themoviedb.domain.usecase.GetTopFiveMoviesUseCase
import com.alaishat.ahmed.themoviedb.domain.usecase.ObserveConnectionStateUseCase
import com.alaishat.ahmed.themoviedb.presentation.architecture.HomeTab
import com.alaishat.ahmed.themoviedb.presentation.architecture.AutoRefreshableViewModel
import com.alaishat.ahmed.themoviedb.presentation.architecture.result.asResult
import com.alaishat.ahmed.themoviedb.presentation.common.model.Movie
import com.alaishat.ahmed.themoviedb.presentation.common.model.toPresentation
import com.alaishat.ahmed.themoviedb.presentation.feature.home.mapper.toMovieListViewState
import com.alaishat.ahmed.themoviedb.presentation.feature.home.model.HomeUiState
import com.alaishat.ahmed.themoviedb.presentation.feature.home.model.MovieListUiState
import com.alaishat.ahmed.themoviedb.presentation.paging.NormalPagingSource
import com.alaishat.ahmed.themoviedb.presentation.paging.defaultPagerOf
import com.alaishat.ahmed.themoviedb.presentation.paging.mapData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMoviesPage: GetMoviesPageUseCase,
    private val getTopFiveMoviesUseCase: GetTopFiveMoviesUseCase,
    connectionStateProvider: ObserveConnectionStateUseCase,
) : AutoRefreshableViewModel<HomeUiState>(
    connectionStateProvider
) {

    init {
        loadUiState()
    }

    // Each tab with its flow
    private fun tabs(): Map<HomeTab, Flow<PagingData<Movie>>> = HomeTab.entries.associateWith { tab -> getMoviesFlowOf(tab) }

    private fun getMoviesFlowOf(homeTab: HomeTab) =
        defaultPagerOf {
            NormalPagingSource { page -> getMoviesPage(homeTab.movieListTypeDomainModel, page) }
        }.flow
            .mapData { it.toPresentation() }
            .cachedIn(viewModelScope)

    override fun refreshableUiStateProvider() = lazy {
        getTopFiveMoviesUseCase()
            .map { it.map { movie -> movie.toPresentation() } }
            .asResult()
            .map {
                HomeUiState(
                    topMoviesState = it.toMovieListViewState(),
                    tabs = tabs()
                )
            }
    }

    override fun initialUiStateProvider() = HomeUiState(MovieListUiState.Loading, tabs = tabs())
}