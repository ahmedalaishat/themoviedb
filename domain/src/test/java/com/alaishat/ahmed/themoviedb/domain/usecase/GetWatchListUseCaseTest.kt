package com.alaishat.ahmed.themoviedb.domain.usecase

import com.alaishat.ahmed.themoviedb.domain.common.model.MovieDomainModel
import com.alaishat.ahmed.themoviedb.domain.repository.MoviesRepository
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
class GetWatchListUseCaseTest {

    private lateinit var classUnderTest: GetWatchListUseCase

    @Mock
    private lateinit var moviesRepository: MoviesRepository

    @Before
    fun setUp() {
        classUnderTest = GetWatchListUseCase(moviesRepository = moviesRepository)
    }

    @Test
    fun `Given page, when invoke then returns watchlist page`() = runBlocking {
        // Given
        val page = 1

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
        givenBlocking { moviesRepository.getWatchListPage(page) }
            .willReturn(expectedMovies)

        // When
        val actualValue = classUnderTest.invoke(page)

        // Then
        assertEquals(expectedMovies, actualValue)
    }
}