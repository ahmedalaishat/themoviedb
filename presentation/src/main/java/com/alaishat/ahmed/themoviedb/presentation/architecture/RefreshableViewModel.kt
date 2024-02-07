package com.alaishat.ahmed.themoviedb.presentation.architecture

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Created by Ahmed Al-Aishat on Dec/24/2023.
 * The Movie DB Project.
 */
abstract class RefreshableViewModel<T> : BaseViewModel() {
    private val _refreshableUiState by lazy {
        MutableStateFlow(RefreshableUiState(initialUiStateProvider(), false))
    }
    val refreshableUiState = _refreshableUiState.asStateFlow()

    abstract fun initialUiStateProvider(): T
    abstract fun refreshableUiStateProvider(): Lazy<Flow<T>>

    open fun loadUiState() {
        viewModelScope.launch {
            refreshableUiStateProvider().value.collect { newState ->
                _refreshableUiState.update { it.copy(uiState = newState, isRefreshing = false) }
            }
        }
    }

    fun refresh() {
        _refreshableUiState.update { it.copy(isRefreshing = true) }
        viewModelScope.launch {
            delay(500)
            loadUiState()
        }
    }
}