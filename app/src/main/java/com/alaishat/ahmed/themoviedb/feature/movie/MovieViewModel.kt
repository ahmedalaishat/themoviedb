package com.alaishat.ahmed.themoviedb.feature.movie

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.alaishat.ahmed.themoviedb.architecture.BaseViewModel
import com.alaishat.ahmed.themoviedb.domain.usecase.AddMovieRatingUseCase
import com.alaishat.ahmed.themoviedb.domain.usecase.GetMovieCreditsUseCase
import com.alaishat.ahmed.themoviedb.domain.usecase.GetMovieDetailsUseCase
import com.alaishat.ahmed.themoviedb.domain.usecase.GetMovieReviewsUseCase
import com.alaishat.ahmed.themoviedb.domain.usecase.ToggleWatchlistMovieUseCase
import com.alaishat.ahmed.themoviedb.feature.movie.navigation.MovieDetailsArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Ahmed Al-Aishat on Sep/09/2023.
 * The Movie DB Project.
 */
@HiltViewModel
class MovieViewModel @Inject constructor(
    private val addMovieRating: AddMovieRatingUseCase,
    private val toggleWatchlistMovie: ToggleWatchlistMovieUseCase,
    getMovieDetails: GetMovieDetailsUseCase,
    getMovieReviews: GetMovieReviewsUseCase,
    getMovieCredits: GetMovieCreditsUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val args = MovieDetailsArgs(savedStateHandle)

    val movieDetails = getMovieDetails(args.movieId).stateInViewModel(null)
    val movieReviews = getMovieReviews(args.movieId)
    val movieCredits = getMovieCredits(args.movieId).stateInViewModel(null)

    val rated = MutableStateFlow(false)

    fun rateMovie(rating: Int) {
        viewModelScope.launch {
            addMovieRating(args.movieId, rating)
            rated.value = true
        }
    }

    fun toggleWatchlist(watchlist: Boolean) {
        viewModelScope.launch {
            toggleWatchlistMovie(args.movieId, watchlist)
        }
    }
}