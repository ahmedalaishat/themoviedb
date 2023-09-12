package com.alaishat.ahmed.themoviedb.feature.search

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.alaishat.ahmed.themoviedb.architecture.BaseViewModel
import com.alaishat.ahmed.themoviedb.domain.usecase.SearchMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    val searchMoviesFlow = searchMovieUseCase(queryFlow)
        .cachedIn(viewModelScope)

    fun updateQueryText(query: String) = _queryFlow.update { query }
}