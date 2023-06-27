package com.alaishat.ahmed.themoviedb.feature.home

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.alaishat.ahmed.themoviedb.common.BaseViewModel
import com.alaishat.ahmed.themoviedb.common.HomeTab
import com.alaishat.ahmed.themoviedb.common.result.Result
import com.alaishat.ahmed.themoviedb.common.result.asResult
import com.alaishat.ahmed.themoviedb.common.usermessage.UserMessage
import com.alaishat.ahmed.themoviedb.domain.GetMoviesPagingFlowUseCase
import com.alaishat.ahmed.themoviedb.domain.GetTopFiveMoviesUseCase
import com.alaishat.ahmed.themoviedb.domain.model.Movie
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
        return getMoviesPagingFlowUseCase(homeTab.movieListType).cachedIn(viewModelScope)
    }

    val topFiveMoviesFlow = getTopFiveMoviesUseCase()
        .asResult()
        .map(Result<List<Movie>>::toMovieListUiState)
        .stateInViewModel(
            initialValue = MovieListUiState.Loading,
            started = SharingStarted.Lazily // no need to use `WhileSubscribed` as we make one shot call
        )

    // Each tab with its flow
    val tabs: Map<HomeTab, Flow<PagingData<Movie>>> = HomeTab.values().associateWith { tab -> getMoviesFlowOf(tab) }
}


sealed interface MovieListUiState {
    object Loading : MovieListUiState

    data class Error(
        val message: UserMessage,
    ) : MovieListUiState

    data class Success(
        val movies: List<Movie>,
    ) : MovieListUiState
}

fun Result<List<Movie>>.toMovieListUiState(): MovieListUiState {
    return when (this) {
        is Result.Loading -> MovieListUiState.Loading
        is Result.Error -> MovieListUiState.Error(this.message)
        is Result.Success -> MovieListUiState.Success(this.data)
    }
}