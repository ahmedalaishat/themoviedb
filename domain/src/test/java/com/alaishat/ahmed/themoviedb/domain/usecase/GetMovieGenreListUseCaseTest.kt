package com.alaishat.ahmed.themoviedb.domain.usecase

import com.alaishat.ahmed.themoviedb.domain.common.model.GenreDomainModel
import com.alaishat.ahmed.themoviedb.domain.common.model.GenresDomainModel
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
class GetMovieGenreListUseCaseTest {


    private lateinit var classUnderTest: GetMovieGenreListUseCase

    @Mock
    private lateinit var moviesRepository: MoviesRepository

    @Before
    fun setUp() {
        classUnderTest = GetMovieGenreListUseCase(moviesRepository = moviesRepository)
    }

    @Test
    fun `Given genres when invoke then returns genres`() = runBlocking {
        // Given

        val genre = GenreDomainModel(
            id = 1,
            name = "name"
        )
        val expectedValue = GenresDomainModel.Success(
            listOf(genre)
        )
        givenBlocking { moviesRepository.getMovieGenreList() }
            .willReturn(flowOf(expectedValue))

        // When
        val actualValue = classUnderTest.invoke().first()

        // Then
        assertEquals(expectedValue, actualValue)
    }
}