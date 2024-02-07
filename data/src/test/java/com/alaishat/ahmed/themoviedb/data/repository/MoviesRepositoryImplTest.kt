package com.alaishat.ahmed.themoviedb.data.repository

import com.alaishat.ahmed.themoviedb.data.mapper.MovieDetailsToDomainResolver
import com.alaishat.ahmed.themoviedb.data.mapper.toDataModel
import com.alaishat.ahmed.themoviedb.data.mapper.toDomainModel
import com.alaishat.ahmed.themoviedb.data.model.GenreDataModel
import com.alaishat.ahmed.themoviedb.data.model.MovieDataModel
import com.alaishat.ahmed.themoviedb.data.model.MovieListTypeDataModel
import com.alaishat.ahmed.themoviedb.data.source.local.LocalMoviesDataSource
import com.alaishat.ahmed.themoviedb.data.source.remote.RemoteMoviesDataSource
import com.alaishat.ahmed.themoviedb.domain.common.model.ConnectionStateDomainModel
import com.alaishat.ahmed.themoviedb.domain.common.model.GenreDomainModel
import com.alaishat.ahmed.themoviedb.domain.common.model.GenresDomainModel
import com.alaishat.ahmed.themoviedb.domain.common.model.MovieDomainModel
import com.alaishat.ahmed.themoviedb.domain.common.model.MovieListTypeDomainModel
import com.alaishat.ahmed.themoviedb.domain.repository.ConnectionRepository
import io.mockk.every
import io.mockk.mockkStatic
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.given
import org.mockito.kotlin.givenBlocking
import tech.cloudsystems.alyarzreservations.coroutine.MainDispatcherRule

/**
 * Created by Ahmed Al-Aishat on Feb/07/2024.
 * The Movie DB Project.
 */
@RunWith(MockitoJUnitRunner::class)
class MoviesRepositoryImplTest {
    private val mappersPackage = "com.alaishat.ahmed.themoviedb.data.mapper"

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var classUnderTest: MoviesRepositoryImpl

    @Mock
    private lateinit var remoteMoviesDataSource: RemoteMoviesDataSource

    @Mock
    private lateinit var localMoviesDataSource: LocalMoviesDataSource

    @Mock
    private lateinit var movieDetailsToDomainResolver: MovieDetailsToDomainResolver

    @Mock
    private lateinit var connectionRepository: ConnectionRepository

    @Before
    fun setUp() {
        // Given
        val givenConnectionState = ConnectionStateDomainModel.Connected(backOnline = false)

        givenBlocking {
            connectionRepository.getConnectionState()
        }.willReturn(givenConnectionState)

        classUnderTest = MoviesRepositoryImpl(
            remoteMoviesDataSource = remoteMoviesDataSource,
            localMoviesDataSource = localMoviesDataSource,
            movieDetailsToDomainResolver = movieDetailsToDomainResolver,
            connectionRepository = connectionRepository,
            ioDispatcher = mainDispatcherRule.testDispatcher,
            coroutineScope = TestScope(mainDispatcherRule.testDispatcher),
        )
    }

    @Test
    fun `Given cached genres, when getMovieGenreList then returns genres`() = runTest {
        // Given
        val givenConnectionState = ConnectionStateDomainModel.Connected(backOnline = false)
        val givenGenre = GenreDataModel(
            id = 1,
            name = "name",
        )
        val expectedGenre = GenreDomainModel(
            id = 1,
            name = "name",
        )

        given {
            localMoviesDataSource.getMovieGenreList()
        }.willReturn(listOf(givenGenre))

        mockkStatic("$mappersPackage.GenreDataToDomainMapperKt")
        every { givenGenre.toDomainModel() } returns expectedGenre

        classUnderTest = MoviesRepositoryImpl(
            remoteMoviesDataSource = remoteMoviesDataSource,
            localMoviesDataSource = localMoviesDataSource,
            movieDetailsToDomainResolver = movieDetailsToDomainResolver,
            connectionRepository = connectionRepository,
            ioDispatcher = mainDispatcherRule.testDispatcher,
            coroutineScope = TestScope(mainDispatcherRule.testDispatcher),
        )

        // When
        val actualGenres = (classUnderTest.getMovieGenreList().first() as GenresDomainModel.Success).genres

        // Then
        assertEquals(listOf(expectedGenre), actualGenres)
    }

    @Test
    fun `Given top five movies when getTopFiveMovies then returns top five movies`() = runTest {
        // Given
        val givenMovieListType = MovieListTypeDataModel.TOP_FIVE
        val givenPage = 1
        val givenMovie = MovieDataModel(
            id = 1,
            backdropPath = "",
            overview = "",
            posterPath = "",
            releaseDate = "",
            title = "",
            voteAverage = 0.0f,
        )
        val expectedMovie = MovieDomainModel(
            id = 1,
            overview = "",
            posterPath = "",
            backdropPath = "",
            releaseDate = "",
            title = "",
            voteAverage = "",
        )

        givenBlocking {
            remoteMoviesDataSource.getMoviesPage(givenMovieListType, givenPage)
        }.willReturn(listOf(givenMovie))

        mockkStatic("$mappersPackage.MovieDataToDomainMapperKt")
        every { givenMovie.toDomainModel() } returns expectedMovie

        // When
        val actualValue = classUnderTest.getTopFiveMovies().first()

        // Then
        assertEquals(listOf(expectedMovie), actualValue)
    }

    @Test
    fun `Given movie list type, connection state, and page when getMoviesPageByType then returns movies page`() =
        runTest {
            // Given
            val dataMovieListType = MovieListTypeDataModel.TOP_RATED
            val domainMovieListType = MovieListTypeDomainModel.TOP_RATED
            val givenPage = 1

            val givenMovie = MovieDataModel(
                id = 1,
                backdropPath = "",
                overview = "",
                posterPath = "",
                releaseDate = "",
                title = "",
                voteAverage = 0.0f,
            )
            val expectedMovie = MovieDomainModel(
                id = 1,
                overview = "",
                posterPath = "",
                backdropPath = "",
                releaseDate = "",
                title = "",
                voteAverage = "",
            )

            givenBlocking {
                remoteMoviesDataSource.getMoviesPage(dataMovieListType, givenPage)
            }.willReturn(listOf(givenMovie))

            mockkStatic("$mappersPackage.MovieDataToDomainMapperKt")
            every { givenMovie.toDomainModel() } returns expectedMovie

            mockkStatic("$mappersPackage.MovieListTypeDomainToDataMapperKt")
            every { domainMovieListType.toDataModel() } returns dataMovieListType

            // When
            val actualValue = classUnderTest.getMoviesPageByType(domainMovieListType, givenPage)

            // Then
            assertEquals(listOf(expectedMovie), actualValue)
        }
}