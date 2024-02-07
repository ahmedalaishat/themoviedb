package com.alaishat.ahmed.themoviedb.domain.usecase

import com.alaishat.ahmed.themoviedb.domain.common.model.MovieDomainModel
import com.alaishat.ahmed.themoviedb.domain.common.model.MovieListTypeDomainModel
import com.alaishat.ahmed.themoviedb.domain.repository.MoviesRepository
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
class SearchMovieUseCaseTest{
    private lateinit var classUnderTest: SearchMovieUseCase

    @Mock
    private lateinit var moviesRepository: MoviesRepository

    @Before
    fun setUp() {
        classUnderTest = SearchMovieUseCase(moviesRepository = moviesRepository)
    }

    @Test
    fun `Given query, and page when invoke then returns movies page`() = runBlocking {
        // Given
        val query = "query"
        val page = 2

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
        givenBlocking { moviesRepository.getSearchMoviePage(query, page) }
            .willReturn(expectedMovies)

        // When
        val actualValue = classUnderTest.invoke(query, page)

        // Then
        assertEquals(expectedMovies, actualValue)
    }
}