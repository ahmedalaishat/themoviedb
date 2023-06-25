package com.alaishat.ahmed.themoviedb.feature.home

import com.alaishat.ahmed.themoviedb.common.BaseViewModel
import com.alaishat.ahmed.themoviedb.common.HomeTab
import com.alaishat.ahmed.themoviedb.common.result.Result
import com.alaishat.ahmed.themoviedb.common.result.asResult
import com.alaishat.ahmed.themoviedb.common.usermessage.UserMessage
import com.alaishat.ahmed.themoviedb.domain.GetMovieListUseCase
import com.alaishat.ahmed.themoviedb.domain.GetTopFiveMoviesUseCase
import com.alaishat.ahmed.themoviedb.domain.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMovieListUseCase: GetMovieListUseCase,
    getTopFiveMoviesUseCase: GetTopFiveMoviesUseCase,
) : BaseViewModel() {

    private fun getMoviesFlowOf(homeTab: HomeTab): StateFlow<MovieListUiState> {
        return getMovieListUseCase(homeTab.movieListType)
            .asResult()
            .map(Result<List<Movie>>::toMovieListUiState)
            .stateInViewModel(
                initialValue = MovieListUiState.Loading,
                started = SharingStarted.Lazily // no need to use `WhileSubscribed` as we make one shot call
            )
    }

    //AHMED_TODO: make me pager flow
    val topFiveMoviesFlow = getTopFiveMoviesUseCase()
        .asResult()
        .map(Result<List<Movie>>::toMovieListUiState)
        .stateInViewModel(
            initialValue = MovieListUiState.Loading,
            started = SharingStarted.Lazily
        )

    // Each tab with its flow
    val tabs: Map<HomeTab, StateFlow<MovieListUiState>> = HomeTab.values().associateWith { tab -> getMoviesFlowOf(tab) }
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