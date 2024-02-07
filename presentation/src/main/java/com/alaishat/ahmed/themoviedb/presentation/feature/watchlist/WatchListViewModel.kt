package com.alaishat.ahmed.themoviedb.presentation.feature.watchlist

import androidx.paging.filter
import androidx.paging.map
import com.alaishat.ahmed.themoviedb.presentation.common.constants.DEBOUNCE_TIMEOUT
import com.alaishat.ahmed.themoviedb.domain.usecase.GetWatchListUseCase
import com.alaishat.ahmed.themoviedb.presentation.architecture.BaseViewModel
import com.alaishat.ahmed.themoviedb.presentation.common.model.toPresentation
import com.alaishat.ahmed.themoviedb.presentation.paging.NormalPagingSource
import com.alaishat.ahmed.themoviedb.presentation.paging.defaultPagerOf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
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

    private val _queryFlow = MutableStateFlow("")
    val queryFlow = _queryFlow.asStateFlow()

    private val pagingFlow = defaultPagerOf(
        pagingSourceFactory = {
            NormalPagingSource { page -> getWatchListUseCase(page) }
        }
    ).flow


    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val watchListFlow = queryFlow
        .debounce(DEBOUNCE_TIMEOUT)
        .flatMapLatest { query ->
            pagingFlow.map { list ->
                list.filter { movie ->
                    movie.title.contains(query, ignoreCase = true) ||
                            movie.overview.contains(query, ignoreCase = true)
                }.map {
                    it.toPresentation()
                }
            }
        }

    fun updateQueryText(query: String) = _queryFlow.update { query }
}