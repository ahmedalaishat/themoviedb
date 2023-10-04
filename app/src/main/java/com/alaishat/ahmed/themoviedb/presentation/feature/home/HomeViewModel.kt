package com.alaishat.ahmed.themoviedb.presentation.feature.home

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.alaishat.ahmed.themoviedb.architecture.BaseViewModel
import com.alaishat.ahmed.themoviedb.architecture.HomeTab
import com.alaishat.ahmed.themoviedb.architecture.result.Result
import com.alaishat.ahmed.themoviedb.architecture.result.asResult
import com.alaishat.ahmed.themoviedb.data.architecture.mapData
import com.alaishat.ahmed.themoviedb.domain.usecase.GetMoviesPagingFlowUseCase
import com.alaishat.ahmed.themoviedb.domain.usecase.GetTopFiveMoviesUseCase
import com.alaishat.ahmed.themoviedb.presentation.common.model.Movie
import com.alaishat.ahmed.themoviedb.presentation.common.model.toPresentation
import com.alaishat.ahmed.themoviedb.presentation.feature.home.mapper.toMovieListViewState
import com.alaishat.ahmed.themoviedb.presentation.feature.home.model.MovieListViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMoviesPagingFlowUseCase: GetMoviesPagingFlowUseCase,
    getTopFiveMoviesUseCase: GetTopFiveMoviesUseCase,
) : BaseViewModel() {

    private fun getMoviesFlowOf(homeTab: HomeTab): Flow<PagingData<Movie>> {
        return getMoviesPagingFlowUseCase(homeTab.movieListTypeDomainModel)
            .mapData { it.toPresentation() }
            .cachedIn(viewModelScope)
    }

    val topFiveMoviesFlow = getTopFiveMoviesUseCase()
        .map { it.map { movie -> movie.toPresentation() } }
        .asResult()
        .map(Result<List<Movie>>::toMovieListViewState)
        .stateInViewModel(
            initialValue = MovieListViewState.Loading,
            started = SharingStarted.Lazily // no need to use `WhileSubscribed` as we make one shot call
        )

    // Each tab with its flow
    val tabs: Map<HomeTab, Flow<PagingData<Movie>>> =
        HomeTab.values().associateWith { tab -> getMoviesFlowOf(tab) }
}