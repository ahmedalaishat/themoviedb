package com.alaishat.ahmed.themoviedb.presentation.architecture

import androidx.lifecycle.viewModelScope
import com.alaishat.ahmed.themoviedb.domain.common.model.ConnectionStateDomainModel
import com.alaishat.ahmed.themoviedb.domain.usecase.ObserveConnectionStateUseCase
import kotlinx.coroutines.launch

/**
 * Created by Ahmed Al-Aishat on Dec/24/2023.
 * The Movie DB Project.
 */
abstract class AutoRefreshableViewModel<T>(
    connectionStateProvider: ObserveConnectionStateUseCase,
) : RefreshableViewModel<T>() {
    private var hasLoaded = false

    override fun loadUiState() {
        super.loadUiState()
        hasLoaded = true
    }

    init {
        viewModelScope.launch {
            connectionStateProvider().collect {
                if (it is ConnectionStateDomainModel.Connected && it.backOnline && hasLoaded) {
                    refresh()
                }
            }
        }
    }

}