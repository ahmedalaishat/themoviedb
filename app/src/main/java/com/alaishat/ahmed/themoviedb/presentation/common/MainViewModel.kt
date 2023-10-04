package com.alaishat.ahmed.themoviedb.presentation.common

import com.alaishat.ahmed.themoviedb.architecture.BaseViewModel
import com.alaishat.ahmed.themoviedb.domain.model.GenresDomainModel
import com.alaishat.ahmed.themoviedb.domain.usecase.GetMovieGenreListUseCase
import com.alaishat.ahmed.themoviedb.presentation.common.mapper.toViewState
import com.alaishat.ahmed.themoviedb.presentation.common.model.MainActivityUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by Ahmed Al-Aishat on Sep/12/2023.
 * The Movie DB Project.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    getMovieGenreListUseCase: GetMovieGenreListUseCase,
) : BaseViewModel() {

    val uiState = getMovieGenreListUseCase()
        .map(GenresDomainModel::toViewState)
        .stateInViewModel(MainActivityUiState.Loading)

}