package com.alaishat.ahmed.themoviedb.presentation.common

import com.alaishat.ahmed.themoviedb.domain.usecase.GetMovieGenreListUseCase
import com.alaishat.ahmed.themoviedb.domain.usecase.ObserveConnectionStateUseCase
import com.alaishat.ahmed.themoviedb.presentation.architecture.BaseViewModel
import com.alaishat.ahmed.themoviedb.presentation.common.mapper.toUiModel
import com.alaishat.ahmed.themoviedb.presentation.common.mapper.toViewState
import com.alaishat.ahmed.themoviedb.presentation.common.model.MainActivityUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

/**
 * Created by Ahmed Al-Aishat on Sep/12/2023.
 * The Movie DB Project.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    getMovieGenreList: GetMovieGenreListUseCase,
    observeConnection: ObserveConnectionStateUseCase,
) : BaseViewModel() {

    val uiState = getMovieGenreList().combine(observeConnection()) { genres, connection ->
        genres.toViewState(connection.toUiModel())
    }
        .stateInViewModel(MainActivityUiState.Loading)

}