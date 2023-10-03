package com.alaishat.ahmed.themoviedb.presentation.feature.movie

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.alaishat.ahmed.themoviedb.architecture.BaseViewModel
import com.alaishat.ahmed.themoviedb.data.architecture.mapData
import com.alaishat.ahmed.themoviedb.domain.feature.movie.model.CreditsDomainModel
import com.alaishat.ahmed.themoviedb.domain.feature.movie.model.MovieDetailsDomainModel
import com.alaishat.ahmed.themoviedb.domain.feature.movie.model.ReviewDomainModel
import com.alaishat.ahmed.themoviedb.domain.usecase.AddMovieRatingUseCase
import com.alaishat.ahmed.themoviedb.domain.usecase.GetMovieCreditsUseCase
import com.alaishat.ahmed.themoviedb.domain.usecase.GetMovieDetailsUseCase
import com.alaishat.ahmed.themoviedb.domain.usecase.GetMovieReviewsPagingFlowUseCase
import com.alaishat.ahmed.themoviedb.domain.usecase.ToggleWatchlistMovieUseCase
import com.alaishat.ahmed.themoviedb.presentation.feature.movie.mapper.toViewState
import com.alaishat.ahmed.themoviedb.presentation.feature.movie.model.CreditsViewState
import com.alaishat.ahmed.themoviedb.presentation.feature.movie.model.MovieDetailsViewState
import com.alaishat.ahmed.themoviedb.presentation.feature.movie.model.toPresentation
import com.alaishat.ahmed.themoviedb.ui.feature.movie.navigation.MovieDetailsArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
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
    getPagingMovieReviews: GetMovieReviewsPagingFlowUseCase,
    getMovieCredits: GetMovieCreditsUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val args = MovieDetailsArgs(savedStateHandle)

    val movieDetails = getMovieDetails(args.movieId)
        .map(MovieDetailsDomainModel::toViewState)
        .stateInViewModel(MovieDetailsViewState.Loading)

    val movieReviews = getPagingMovieReviews(args.movieId)
        .mapData(ReviewDomainModel::toPresentation)

    val movieCredits = getMovieCredits(args.movieId)
        .map(CreditsDomainModel::toViewState)
        .stateInViewModel(CreditsViewState.Loading)

    val rated = MutableStateFlow<Boolean?>(null)

    fun rateMovie(rating: Int) {
        viewModelScope.launch {
            rated.value = null
            rated.value = addMovieRating(args.movieId, rating)
        }
    }

    fun toggleWatchlist(watchlist: Boolean) {
        viewModelScope.launch {
            toggleWatchlistMovie(args.movieId, watchlist)
        }
    }
}