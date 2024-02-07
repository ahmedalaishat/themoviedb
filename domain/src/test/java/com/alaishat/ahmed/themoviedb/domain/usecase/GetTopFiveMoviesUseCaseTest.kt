package com.alaishat.ahmed.themoviedb.domain.usecase

import com.alaishat.ahmed.themoviedb.domain.common.model.MovieDomainModel
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
class GetTopFiveMoviesUseCaseTest {

    private lateinit var classUnderTest: GetTopFiveMoviesUseCase

    @Mock
    private lateinit var moviesRepository: MoviesRepository

    @Before
    fun setUp() {
        classUnderTest = GetTopFiveMoviesUseCase(moviesRepository = moviesRepository)
    }

    @Test
    fun `Given top five movies When invoke then returns top five movies`() = runBlocking {
        // Given

        val expectedMovies = listOf(
            MovieDomainModel(
                id = 1,
                overview = "overview",
                posterPath = "posterPath",
                backdropPath = "backdropPath",
                title = "title",
                voteAverage = "voteAverage",
                releaseDate = "releaseDate",
            )
        )
        givenBlocking { moviesRepository.getTopFiveMovies() }
            .willReturn(flowOf(expectedMovies))

        // When
        val actualValue = classUnderTest.invoke().first()

        // Then
        assertEquals(expectedMovies, actualValue)
    }
}