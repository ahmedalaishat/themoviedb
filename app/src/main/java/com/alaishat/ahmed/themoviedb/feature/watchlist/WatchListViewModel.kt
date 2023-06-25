package com.alaishat.ahmed.themoviedb.feature.watchlist

import com.alaishat.ahmed.themoviedb.common.BaseViewModel
import com.alaishat.ahmed.themoviedb.common.result.Result
import com.alaishat.ahmed.themoviedb.common.result.asResult
import com.alaishat.ahmed.themoviedb.common.usermessage.UserMessage
import com.alaishat.ahmed.themoviedb.domain.GetWatchListUseCase
import com.alaishat.ahmed.themoviedb.domain.model.Movie
import com.alaishat.ahmed.themoviedb.feature.search.SearchUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * Created by Ahmed Al-Aishat on Jun/26/2023.
 * The Movie DB Project.
 */
@HiltViewModel
class WatchListViewModel @Inject constructor(
    getWatchListUseCase: GetWatchListUseCase,
) : BaseViewModel() {

    //AHMED_TODO: support watch list search
    private val _queryFlow = MutableStateFlow("")
    val queryFlow = _queryFlow.asStateFlow()

    //AHMED_TODO: make me pager flow
    val searchMoviesFlow = getWatchListUseCase()
        .asResult()
        .map { it.toWatchListUiState(queryFlow.value) }
        .stateInViewModel(
            initialValue = WatchListUiState.Loading,
            started = SharingStarted.Lazily // no need to use `WhileSubscribed` as we make one shot call
        )

    fun updateQueryText(query: String) = _queryFlow.update { query }
}

sealed interface WatchListUiState {
    object NoResults : WatchListUiState
    object Loading : WatchListUiState

    data class Error(
        val message: UserMessage,
    ) : WatchListUiState

    data class Success(
        val movies: List<Movie>,
    ) : WatchListUiState

}

private fun Result<List<Movie>>.toWatchListUiState(query: String): WatchListUiState {
    return when (this) {
        is Result.Loading -> WatchListUiState.Loading
        is Result.Error -> WatchListUiState.Error(this.message)
        is Result.Success -> if (this.data.isEmpty()) WatchListUiState.NoResults else WatchListUiState.Success(this.data)
    }
}