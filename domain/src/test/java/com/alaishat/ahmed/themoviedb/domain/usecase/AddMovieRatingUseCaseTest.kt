package com.alaishat.ahmed.themoviedb.domain.usecase

import com.alaishat.ahmed.themoviedb.domain.repository.MoviesRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
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
class AddMovieRatingUseCaseTest {
    private lateinit var classUnderTest: AddMovieRatingUseCase

    @Mock
    private lateinit var moviesRepository: MoviesRepository

    @Before
    fun setUp() {
        classUnderTest =
            AddMovieRatingUseCase(moviesRepository = moviesRepository)
    }

    @Test
    fun `Given movieId, and rating when invoke then submit movie rating`() = runBlocking {
        // Given
        val movieId = 1
        val rating = 8

        val expectedValue = true
        givenBlocking { moviesRepository.addMovieRating(movieId, rating) }
            .willReturn(expectedValue)

        // When
        val actualValue = classUnderTest.invoke(movieId, rating)

        // Then
        Assert.assertEquals(expectedValue, actualValue)
    }
}