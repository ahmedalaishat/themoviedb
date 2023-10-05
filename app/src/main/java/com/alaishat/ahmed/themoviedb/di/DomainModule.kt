package com.alaishat.ahmed.themoviedb.di

import com.alaishat.ahmed.themoviedb.domain.repository.AccountRepository
import com.alaishat.ahmed.themoviedb.domain.repository.MoviesRepository
import com.alaishat.ahmed.themoviedb.domain.usecase.AddMovieRatingUseCase
import com.alaishat.ahmed.themoviedb.domain.usecase.GetMovieCreditsUseCase
import com.alaishat.ahmed.themoviedb.domain.usecase.GetMovieDetailsUseCase
import com.alaishat.ahmed.themoviedb.domain.usecase.GetMovieGenreListUseCase
import com.alaishat.ahmed.themoviedb.domain.usecase.GetMovieReviewsPagingFlowUseCase
import com.alaishat.ahmed.themoviedb.domain.usecase.GetMoviesPagingFlowUseCase
import com.alaishat.ahmed.themoviedb.domain.usecase.GetTopFiveMoviesUseCase
import com.alaishat.ahmed.themoviedb.domain.usecase.GetWatchListUseCase
import com.alaishat.ahmed.themoviedb.domain.usecase.SearchMovieUseCase
import com.alaishat.ahmed.themoviedb.domain.usecase.ToggleWatchlistMovieUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by Ahmed Al-Aishat on Sep/16/2023.
 * The Movie DB Project.
 */
@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    fun providesAddMovieRatingUseCase(
        moviesRepository: MoviesRepository,
    ) = AddMovieRatingUseCase(
        moviesRepository = moviesRepository,
    )

    @Provides
    fun providesGetMovieCreditsUseCase(
        moviesRepository: MoviesRepository,
    ) = GetMovieCreditsUseCase(
        moviesRepository = moviesRepository,
    )

    @Provides
    fun providesGetMovieGenreListUseCase(
        moviesRepository: MoviesRepository,
    ) = GetMovieGenreListUseCase(
        moviesRepository = moviesRepository,
    )

    @Provides
    fun providesGetMovieReviewsPagingFlowUseCase(
        moviesRepository: MoviesRepository,
    ) = GetMovieReviewsPagingFlowUseCase(
        moviesRepository = moviesRepository,
    )

    @Provides
    fun providesGetMoviesPagingFlowUseCase(
        moviesRepository: MoviesRepository,
    ) = GetMoviesPagingFlowUseCase(
        moviesRepository = moviesRepository,
    )

    @Provides
    fun providesGetTopFiveMoviesUseCase(
        moviesRepository: MoviesRepository,
    ) = GetTopFiveMoviesUseCase(
        moviesRepository = moviesRepository,
    )

    @Provides
    fun providesGetWatchListUseCase(
        moviesRepository: MoviesRepository,
    ) = GetWatchListUseCase(
        moviesRepository = moviesRepository,
    )

    @Provides
    fun providesSearchMovieUseCase(
        moviesRepository: MoviesRepository,
    ) = SearchMovieUseCase(
        moviesRepository = moviesRepository,
    )

    @Provides
    fun providesGetMovieDetailsUseCase(
        moviesRepository: MoviesRepository
    ) = GetMovieDetailsUseCase(
        moviesRepository = moviesRepository,
    )


    @Provides
    fun providesToggleWatchlistMovieUseCase(
        moviesRepository: MoviesRepository,
    ) = ToggleWatchlistMovieUseCase(
        moviesRepository = moviesRepository,
    )

}