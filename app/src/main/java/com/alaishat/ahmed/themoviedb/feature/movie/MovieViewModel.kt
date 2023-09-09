package com.alaishat.ahmed.themoviedb.feature.movie

import androidx.lifecycle.SavedStateHandle
import com.alaishat.ahmed.themoviedb.common.BaseViewModel
import com.alaishat.ahmed.themoviedb.domain.GetMovieDetailsUseCase
import com.alaishat.ahmed.themoviedb.domain.model.MovieDetails
import com.alaishat.ahmed.themoviedb.feature.movie.navigation.MovieDetailsArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * Created by Ahmed Al-Aishat on Sep/09/2023.
 * The Movie DB Project.
 */
@HiltViewModel
class MovieViewModel @Inject constructor(
    getMovieDetails: GetMovieDetailsUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val args = MovieDetailsArgs(savedStateHandle)

    val movieDetails = getMovieDetails(args.movieId).stateInViewModel(null)


}