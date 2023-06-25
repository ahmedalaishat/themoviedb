package com.alaishat.ahmed.themoviedb.feature.search

import com.alaishat.ahmed.themoviedb.common.BaseViewModel
import com.alaishat.ahmed.themoviedb.common.result.Result
import com.alaishat.ahmed.themoviedb.common.result.asResult
import com.alaishat.ahmed.themoviedb.common.usermessage.UserMessage
import com.alaishat.ahmed.themoviedb.domain.SearchMovieUseCase
import com.alaishat.ahmed.themoviedb.domain.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    searchMovieUseCase: SearchMovieUseCase,
) : BaseViewModel() {

    private val _queryFlow = MutableStateFlow("")
    val queryFlow = _queryFlow.asStateFlow()

    //AHMED_TODO: make me pager flow
    val searchMoviesFlow = searchMovieUseCase(queryFlow)
        .asResult()
        .map { it.toSearchUiState(queryFlow.value) }
        .stateInViewModel(
            initialValue = SearchUiState.Initial,
            started = SharingStarted.Lazily // no need to use `WhileSubscribed` as we make one shot call
        )

    fun updateQueryText(query: String) = _queryFlow.update { query }
}

sealed interface SearchUiState {
    object Initial : SearchUiState
    object NoResults : SearchUiState
    object Loading : SearchUiState

    data class Error(
        val message: UserMessage,
    ) : SearchUiState

    data class Success(
        val movies: List<Movie>,
    ) : SearchUiState

}

private fun Result<List<Movie>>.toSearchUiState(query: String): SearchUiState {
    return if (query.isEmpty()) SearchUiState.Initial
    else when (this) {
        is Result.Loading -> SearchUiState.Loading
        is Result.Error -> SearchUiState.Error(this.message)
        is Result.Success -> if (this.data.isEmpty()) SearchUiState.NoResults else SearchUiState.Success(this.data)
    }
}