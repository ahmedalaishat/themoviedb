package com.alaishat.ahmed.themoviedb.datasource.movie.source.local

import androidx.paging.PagingData
import app.cash.sqldelight.coroutines.asFlow
import com.alaishat.ahmed.themoviedb.data.model.CreditDataModel
import com.alaishat.ahmed.themoviedb.data.model.GenreDataModel
import com.alaishat.ahmed.themoviedb.data.model.MovieDataModel
import com.alaishat.ahmed.themoviedb.data.model.MovieDetailsDataModel
import com.alaishat.ahmed.themoviedb.data.model.MovieListTypeDataModel
import com.alaishat.ahmed.themoviedb.data.model.ReviewDataModel
import com.alaishat.ahmed.themoviedb.data.source.local.LocalMoviesDataSource
import com.alaishat.ahmed.themoviedb.datasource.local.provider.MovieQueriesProvider
import com.alaishat.ahmed.themoviedb.datasource.movie.mapper.mapToGenreDataModels
import com.alaishat.ahmed.themoviedb.datasource.movie.mapper.toDataModel
import com.alaishat.ahmed.themoviedb.datasource.movie.mapper.toEntity
import com.alaishat.ahmed.themoviedb.datasource.movie.mapper.toMovieDataModel
import com.alaishat.ahmed.themoviedb.datasource.movie.mapper.toMovieDetailsDataModel
import com.alaishat.ahmed.themoviedb.datasource.movie.mapper.toReviewDataModel
import comalaishatahmedthemoviedbdatasourcesqldelight.CreditEntity
import comalaishatahmedthemoviedbdatasourcesqldelight.MovieEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by Ahmed Al-Aishat on Sep/12/2023.
 * The Movie DB Project.
 */
class DelightLocalMovieDataSource(
    private val queriesProvider: MovieQueriesProvider,
    val ioDispatcher: CoroutineDispatcher,
) : LocalMoviesDataSource {

    override fun cacheMovieList(
        movieListTypeDataModel: MovieListTypeDataModel,
        deleteCached: Boolean,
        movies: List<MovieDataModel>,
    ) {
        queriesProvider.movie.selectAll().executeAsList()
        movies.forEach { movie ->
            queriesProvider.movie.upsertMovie(movie.toEntity())
        }
        queriesProvider.typeMovie.transaction {
            if (deleteCached)
                queriesProvider.typeMovie.deleteListByType(movieListTypeDataModel)
            movies.forEach { movie ->
                queriesProvider.typeMovie.insert(
                    type = movieListTypeDataModel,
                    movieId = movie.id.toLong()
                )
            }
        }
    }

    override fun cacheMovies(movies: List<MovieDataModel>) {
        queriesProvider.movie.transaction {
            movies.forEach { movie ->
                queriesProvider.movie.upsertMovie(movie.toEntity())
            }
        }
    }

    override fun getCachedMovieList(movieListTypeDataModel: MovieListTypeDataModel): List<MovieDataModel> {
        return queriesProvider.movie.selectMoviesListByType(
            type = movieListTypeDataModel,
        ).executeAsList().map(MovieEntity::toMovieDataModel)
    }

    override fun getCachedWatchlistPage(page: Int): List<MovieDataModel> {
        return queriesProvider.movie.selectMoviesListPage(
            type = MovieListTypeDataModel.WATCHLIST,
            limit = 10,
            offset = (page - 1) * 10L,
        ).executeAsList().map(MovieEntity::toMovieDataModel)
    }

    override fun searchCachedMoviePage(query: String, page: Int): List<MovieDataModel> {
        return queriesProvider.movie.searchMoviesList(
            query = query,
            limit = 10,
            offset = (page - 1) * 10L,
        ).executeAsList().map(MovieEntity::toMovieDataModel)
    }

    override fun getCachedMoviesPagingFlow(
        movieListType: MovieListTypeDataModel,
        page: Int,
    ): List<MovieDataModel> = queriesProvider.movie.selectMoviesPageByType(
        type = movieListType,
        limit = 10,
        offset = (page + 1) * 10L,
    ).executeAsList().map { it.toMovieDataModel() }

    override fun cacheMovieReviews(
        movieId: Int,
        reviews: List<ReviewDataModel>,
    ) {
        queriesProvider.review.transaction {
            reviews.forEach { review ->
                queriesProvider.author.upsertAuthor(review.authorDetailsDataModel.toEntity())
                queriesProvider.review.upsertReview(review.toEntity(movieId = movieId))
            }
        }
    }

    override fun getCachedReviewsPage(movieId: Int, page: Int): List<ReviewDataModel> {
        return queriesProvider.review.selectMoviesReviewsPage(
            movieId = movieId.toLong(),
            limit = 10,
            offset = (page - 1) * 10L,
        ).executeAsList().map { it.toReviewDataModel() }
    }

    override fun getMovieReviewsFlow(movieId: Int): List<ReviewDataModel> {
        return listOf()
//        val a: List<SelectReviewsByMovieId> = reviewEntityQueries.selectReviewsByMovieId(movieId = movieId.toLong()).executeAsList()
    }

    override fun getMovieGenreList(): List<GenreDataModel> {
        return queriesProvider.genre.selectAll().executeAsList().mapToGenreDataModels()
    }

    override fun updateMovieGenreList(genreList: List<GenreDataModel>) {
        queriesProvider.genre.transaction {
            genreList.forEach { genre ->
                queriesProvider.genre.upsertGenre(genre.toEntity())
            }
        }
    }

    override fun cacheMovieCredits(
        movieId: Int,
        credits: List<CreditDataModel>,
    ) {
        queriesProvider.credit.transaction {
            credits.forEach { credit ->
                queriesProvider.credit.upsertCredit(credit.toEntity(movieId = movieId))
            }
        }
    }

    override fun getCachedMovieCredits(movieId: Int): Flow<List<CreditDataModel>> {
        return queriesProvider.credit
            .selectMovieCredits(movieId = movieId.toLong())
            .asFlow()
            .map { it.executeAsList() }
            .map {
                it.map(CreditEntity::toDataModel)
            }
    }

    override fun cacheMovieDetails(movieDetailsDataModel: MovieDetailsDataModel) {
        queriesProvider.movieDetails.upsertMovieDetails(movieDetailsDataModel.toEntity())
        queriesProvider.genre.transaction {
            movieDetailsDataModel.genreDataModels.forEach { genre ->
                queriesProvider.genre.upsertGenre(genre.toEntity())
                queriesProvider.genreMovie.upsertGenreMovieDetails(
                    movieId = movieDetailsDataModel.id.toLong(),
                    genreId = genre.id.toLong()
                )
            }
        }
    }

    override fun getCachedMovieDetails(movieId: Int): MovieDetailsDataModel? {
        return queriesProvider.movie.selectMovieWithDetailsById(movieId = movieId.toLong()).executeAsList()
            .toMovieDetailsDataModel()
    }

    override fun cacheMovieWatchlistStatus(movieId: Int, watchlist: Boolean) {
        if (watchlist)
            queriesProvider.typeMovie.insert(
                type = MovieListTypeDataModel.WATCHLIST,
                movieId = movieId.toLong()
            )
        else
            queriesProvider.typeMovie.deleteByIdAndType(
                type = MovieListTypeDataModel.WATCHLIST,
                movieId = movieId.toLong()
            )
    }

    override fun observeMovieWatchlistStatus(movieId: Int): Flow<Boolean> {
        return queriesProvider.typeMovie.selecctByIdAndType(
            movieId = movieId.toLong(),
            type = MovieListTypeDataModel.WATCHLIST
        ).asFlow().map { it.executeAsOneOrNull() == movieId.toLong() }
    }
}