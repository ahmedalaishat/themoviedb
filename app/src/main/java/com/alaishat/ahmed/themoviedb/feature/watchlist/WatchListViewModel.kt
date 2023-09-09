package com.alaishat.ahmed.themoviedb.feature.watchlist

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.alaishat.ahmed.themoviedb.common.BaseViewModel
import com.alaishat.ahmed.themoviedb.domain.GetWatchListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    val watchListFlow = getWatchListUseCase(queryFlow)
        .cachedIn(viewModelScope)

    fun updateQueryText(query: String) = _queryFlow.update { query }
}