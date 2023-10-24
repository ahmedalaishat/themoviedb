package com.alaishat.ahmed.themoviedb.presentation.architecture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
abstract class BaseViewModel : ViewModel() {

    protected fun <T> Flow<T>.stateInViewModel(
        initialValue: T,
        started: SharingStarted = SharingStarted.WhileSubscribed(5_000),
    ): StateFlow<T> = stateIn(viewModelScope, started, initialValue)
}