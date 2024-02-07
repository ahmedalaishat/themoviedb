package com.alaishat.ahmed.themoviedb.domain.usecase

import com.alaishat.ahmed.themoviedb.domain.common.model.MovieDomainModel
import com.alaishat.ahmed.themoviedb.domain.common.model.MovieListTypeDomainModel
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
class GetMoviesPageUseCaseTest {

    private lateinit var classUnderTest: GetMoviesPageUseCase

    @Mock
    private lateinit var moviesRepository: MoviesRepository

    @Before
    fun setUp() {
        classUnderTest = GetMoviesPageUseCase(moviesRepository = moviesRepository)
    }

    @Test
    fun `Given movies list type, and page when invoke then returns movies page`() = runBlocking {
        // Given
        val moviesListType = MovieListTypeDomainModel.POPULAR
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
        givenBlocking { moviesRepository.getMoviesPageByType(moviesListType, page) }
            .willReturn(expectedMovies)

        // When
        val actualValue = classUnderTest.invoke(moviesListType, page)

        // Then
        assertEquals(expectedMovies, actualValue)
    }
}