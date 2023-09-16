package com.alaishat.ahmed.themoviedb.data.repository

import androidx.paging.PagingData
import com.alaishat.ahmed.themoviedb.data.architecture.mapData
import com.alaishat.ahmed.themoviedb.data.model.MovieDataModel
import com.alaishat.ahmed.themoviedb.data.model.ReviewDataModel
import com.alaishat.ahmed.themoviedb.data.model.mapToCreditsDomainModels
import com.alaishat.ahmed.themoviedb.data.model.mapToGenresDomainModels
import com.alaishat.ahmed.themoviedb.data.model.mapToMovies
import com.alaishat.ahmed.themoviedb.data.model.toMovieDomainModel
import com.alaishat.ahmed.themoviedb.data.model.toMoviesDetailsDomainModel
import com.alaishat.ahmed.themoviedb.data.model.toReviewDomainModel
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.model.MovieListTypeDataModel
import com.alaishat.ahmed.themoviedb.datasource.source.local.LocalMoviesDataSource
import com.alaishat.ahmed.themoviedb.datasource.source.network.RemoteMoviesDataSource
import com.alaishat.ahmed.themoviedb.domain.model.CreditDomainModel
import com.alaishat.ahmed.themoviedb.domain.model.GenreDomainModel
import com.alaishat.ahmed.themoviedb.domain.model.MovieDetailsDomainModel
import com.alaishat.ahmed.themoviedb.domain.model.MovieDomainModel
import com.alaishat.ahmed.themoviedb.domain.model.MovieListTypeDomainModel
import com.alaishat.ahmed.themoviedb.domain.model.ReviewDomainModel
import com.alaishat.ahmed.themoviedb.domain.repository.MoviesRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
class MoviesRepositoryImpl @Inject constructor(
    private val remoteMoviesDataSource: RemoteMoviesDataSource,
    private val localMoviesDataSource: LocalMoviesDataSource,
) : MoviesRepository {
    private val watchlistSet = MutableStateFlow(setOf<Int>())

    override suspend fun getTopFiveMovies(): List<MovieDomainModel> {
        val movies = remoteMoviesDataSource.getMoviesPage(
            movieListTypeDataModel = MovieListTypeDataModel.TOP_FIVE,
            page = 1,
        ).take(5)
        localMoviesDataSource.cacheMovieList(
            deleteCached = true,
            movieListTypeDataModel = MovieListTypeDataModel.TOP_FIVE,
            movies = movies,
        )
        return movies.mapToMovies()
    }

    override fun getMoviesPagingFlowByType(movieListTypeDomainModel: MovieListTypeDomainModel): Flow<PagingData<MovieDomainModel>> {
        val type = MovieListTypeDataModel.getByMovieListTypeDomainModel(movieListTypeDomainModel)

        val pagingFlow = remoteMoviesDataSource.getCacheableMoviesPagingFlow(
            movieListTypeDataModel = type,
            pageCachingHandler = { page, pageData ->
                localMoviesDataSource.cacheMovieList(
                    deleteCached = page == 1,
                    movieListTypeDataModel = MovieListTypeDataModel.getByMovieListTypeDomainModel(
                        movieListTypeDomainModel
                    ),
                    movies = pageData,
                )
            }
        )

        return pagingFlow.mapData(MovieDataModel::toMovieDomainModel)
    }

    override fun getSearchMoviePagingFlow(query: String): Flow<PagingData<MovieDomainModel>> {
        val pagingFlow = remoteMoviesDataSource.getSearchMoviePagingFlow(query = query)
        return pagingFlow.mapData(MovieDataModel::toMovieDomainModel)
    }

    override fun getMovieDetails(movieId: Int): Flow<MovieDetailsDomainModel> = flow {
        coroutineScope {
            val status = async { remoteMoviesDataSource.getMovieAccountStatus(movieId = movieId) }
            val movieDetails = async { remoteMoviesDataSource.getMovieDetails(movieId = movieId) }
//            val movieDetails = async { localMoviesDataSource.getCachedMovieDetails(movieId = movieId) }
            cacheMovieWatchlistStatus(movieId = movieId, watchlist = status.await().watchlist)
            localMoviesDataSource.cacheMovieDetails(movieDetails.await())
            emit(movieDetails.await().toMoviesDetailsDomainModel())
        }
    }

    override suspend fun cacheMovieWatchlistStatus(movieId: Int, watchlist: Boolean) {
        if (watchlist)
            watchlistSet.update { it + movieId }
        else
            watchlistSet.update { it - movieId }
    }

    override fun observeWatchlist(): Flow<Set<Int>> {
        return watchlistSet
    }

    override fun getMovieReviewsPagingFlow(movieId: Int): Flow<PagingData<ReviewDomainModel>> {
        val pagerFlow = remoteMoviesDataSource.getMovieReviewsPagingFlow(
            movieId = movieId,
            pageCachingHandler = { _, reviews ->
                localMoviesDataSource.cacheMovieReviews(movieId = movieId, reviews = reviews)
            }
        )
        return pagerFlow.mapData(ReviewDataModel::toReviewDomainModel)
    }

    override suspend fun getMovieCredits(movieId: Int): List<CreditDomainModel> {
        val credits = remoteMoviesDataSource.getMovieCredits(movieId = movieId)
        localMoviesDataSource.cacheMovieCredits(movieId, credits)
        return credits.mapToCreditsDomainModels()
    }

    override suspend fun addMovieRating(movieId: Int, rating: Int) {
        return remoteMoviesDataSource.addMovieRating(movieId = movieId, rating = rating)
    }

    override fun getMovieGenreList(): Flow<List<GenreDomainModel>> {
        return localMoviesDataSource.getMovieGenreList().map { it.mapToGenresDomainModels() }
    }

    //AHMED_TODO: refactor me
    override suspend fun syncGenres(): Boolean {
        val fetchedGenreList = remoteMoviesDataSource.getMovieGenreList()
        localMoviesDataSource.updateMovieGenreList(fetchedGenreList)
        Timber.e("Syncedd")
        return true
    }
}