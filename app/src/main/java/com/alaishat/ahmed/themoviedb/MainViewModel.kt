package com.alaishat.ahmed.themoviedb

import androidx.lifecycle.viewModelScope
import com.alaishat.ahmed.themoviedb.architecture.BaseViewModel
import com.alaishat.ahmed.themoviedb.domain.model.GenreDomainModel
import com.alaishat.ahmed.themoviedb.domain.usecase.GetMovieGenreListUseCase
import com.alaishat.ahmed.themoviedb.domain.usecase.SyncUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Ahmed Al-Aishat on Sep/12/2023.
 * The Movie DB Project.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    getMovieGenreListUseCase: GetMovieGenreListUseCase,
    private val syncc: SyncUseCase,
) : BaseViewModel() {

    val uiState = getMovieGenreListUseCase()
        .map(MainActivityUiState::Success)
        .stateInViewModel(MainActivityUiState.Loading)


    fun sync() {
        viewModelScope.launch {
            syncc()
        }
    }
}

sealed interface MainActivityUiState {
    object Loading : MainActivityUiState
    data class Success(val genres: List<GenreDomainModel>) : MainActivityUiState
}