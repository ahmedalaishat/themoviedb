package com.alaishat.ahmed.themoviedb.domain.usecase

import com.alaishat.ahmed.themoviedb.domain.feature.movie.model.CreditDomainModel
import com.alaishat.ahmed.themoviedb.domain.feature.movie.model.CreditsDomainModel
import com.alaishat.ahmed.themoviedb.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
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
class GetMovieCreditsUseCaseTest {

    private lateinit var classUnderTest: GetMovieCreditsUseCase

    @Mock
    private lateinit var moviesRepository: MoviesRepository

    @Before
    fun setUp() {
        classUnderTest = GetMovieCreditsUseCase(moviesRepository = moviesRepository)
    }

    @Test
    fun `Given movieId when invoke then returns movie credits`() = runBlocking {
        // Given
        val movieId = 1
        val credit = CreditDomainModel(
            id = "1",
            name = "name",
            profilePath = "profilePath"
        )

        val expectedValue = CreditsDomainModel.Success(listOf(credit))
        givenBlocking { moviesRepository.getMovieCredits(movieId) }
            .willReturn(flowOf(expectedValue))

        // When
        val actualValue = classUnderTest.invoke(movieId).first()

        // Then
        assertEquals(expectedValue, actualValue)
    }
}