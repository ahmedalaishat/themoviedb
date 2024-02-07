package com.alaishat.ahmed.themoviedb.domain.usecase

import com.alaishat.ahmed.themoviedb.domain.feature.movie.model.ReviewDomainModel
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
class GetMovieReviewsPageUseCaseTest {

    private lateinit var classUnderTest: GetMovieReviewsPageUseCase

    @Mock
    private lateinit var moviesRepository: MoviesRepository

    @Before
    fun setUp() {
        classUnderTest = GetMovieReviewsPageUseCase(moviesRepository = moviesRepository)
    }

    @Test
    fun `Given movieId, and page when invoke then returns movie reviews`() = runBlocking {
        // Given
        val movieId = 1
        val page = 2

        val expectedReviews = listOf(
            ReviewDomainModel(
                id = "1",
                content = "content",
                authorName = "authorName",
                authorAvatarPath = "authorAvatarPath",
                rating = "rating",
            )
        )
        givenBlocking { moviesRepository.getMovieReviewsPage(movieId, page = page) }
            .willReturn(expectedReviews)

        // When
        val actualValue = classUnderTest.invoke(movieId, page)

        // Then
        Assert.assertEquals(expectedReviews, actualValue)
    }
}