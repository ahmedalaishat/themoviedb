package com.alaishat.ahmed.themoviedb.presentation.feature.search

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.alaishat.ahmed.themoviedb.presentation.paging.mapData
import com.alaishat.ahmed.themoviedb.domain.common.model.MovieDomainModel
import com.alaishat.ahmed.themoviedb.presentation.common.constants.DEBOUNCE_TIMEOUT
import com.alaishat.ahmed.themoviedb.domain.usecase.SearchMovieUseCase
import com.alaishat.ahmed.themoviedb.presentation.architecture.BaseViewModel
import com.alaishat.ahmed.themoviedb.presentation.common.model.toPresentation
import com.alaishat.ahmed.themoviedb.presentation.paging.NormalPagingSource
import com.alaishat.ahmed.themoviedb.presentation.paging.defaultPagerOf
import com.alaishat.ahmed.themoviedb.presentation.paging.emptyPagingFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    searchMovieUseCase: SearchMovieUseCase,
) : BaseViewModel() {

    private val _queryFlow = MutableStateFlow("")
    val queryFlow = _queryFlow.asStateFlow()

    val searchMoviesFlow = queryFlow
        .debounce(DEBOUNCE_TIMEOUT)
        .flatMapLatest { query ->
            if (query.isEmpty()) emptyPagingFlow()
            else {
                val pager = defaultPagerOf(
                    pagingSourceFactory = {
                        NormalPagingSource { searchMovieUseCase(query, it) }
                    })
                pager.flow
            }
        }
        .mapData(MovieDomainModel::toPresentation)
        .cachedIn(viewModelScope)

    fun updateQueryText(query: String) = _queryFlow.update { query }
}