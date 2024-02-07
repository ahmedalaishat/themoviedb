package com.alaishat.ahmed.themoviedb.domain.usecase

import com.alaishat.ahmed.themoviedb.domain.repository.MoviesRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

/**
 * Created by Ahmed Al-Aishat on Feb/07/2024.
 * The Movie DB Project.
 */
@RunWith(MockitoJUnitRunner::class)
class ToggleWatchlistMovieUseCaseTest {
    private lateinit var classUnderTest: ToggleWatchlistMovieUseCase

    @Mock
    private lateinit var moviesRepository: MoviesRepository

    @Before
    fun setUp() {
        classUnderTest = ToggleWatchlistMovieUseCase(moviesRepository = moviesRepository)
    }

    @Test
    fun `Given movieId, and toggle when invoke then toggle movie watchlist`() = runBlocking {
        // Given
        val movieId = 1
        val toggle = false

        // When
        classUnderTest.invoke(movieId, toggle)

        // Then
        verify(moviesRepository).toggleWatchlistMovie(movieId, toggle)
    }
}