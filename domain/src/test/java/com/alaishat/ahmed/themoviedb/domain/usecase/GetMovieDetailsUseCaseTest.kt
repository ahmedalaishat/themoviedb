package com.alaishat.ahmed.themoviedb.domain.usecase

import com.alaishat.ahmed.themoviedb.domain.feature.movie.model.CreditsDomainModel
import com.alaishat.ahmed.themoviedb.domain.feature.movie.model.MovieDetailsDomainModel
import com.alaishat.ahmed.themoviedb.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.givenBlocking

/**
 * Created by Ahmed Al-Aishat on Feb/07/2024.
 * The Movie DB Project.
 */
@RunWith(MockitoJUnitRunner::class)
class GetMovieDetailsUseCaseTest {

    private lateinit var classUnderTest: GetMovieDetailsUseCase

    @Mock
    private lateinit var moviesRepository: MoviesRepository

    @Before
    fun setUp() {
        classUnderTest = GetMovieDetailsUseCase(moviesRepository = moviesRepository)
    }

    @Test
    fun `Given movieId when invoke then returns movie details`() = runBlocking {
        // Given
        val movieId = 1

        val isWatchlist = false
        val expectedValue = MovieDetailsDomainModel.Success(
            id = 1,
            overview = "overview",
            posterPath = "posterPath",
            backdropPath = "backdropPath",
            releaseYear = "releaseYear",
            title = "title",
            voteAverage = "voteAverage",
            genre = "genre",
            runtime = "runtime",
            watchlist = isWatchlist,
        )
        givenBlocking { moviesRepository.getMovieDetails(movieId) }
            .willReturn(flowOf(expectedValue))

        givenBlocking { moviesRepository.isWatchlist(movieId) }
            .willReturn(flowOf(isWatchlist))

        // When
        val actualValue = classUnderTest.invoke(movieId).first()

        // Then
        assertEquals(expectedValue, actualValue)
    }
}