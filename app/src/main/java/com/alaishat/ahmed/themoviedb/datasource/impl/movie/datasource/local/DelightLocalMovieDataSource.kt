package com.alaishat.ahmed.themoviedb.datasource.impl.movie.datasource.local

import androidx.paging.PagingData
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.paging3.QueryPagingSource
import com.alaishat.ahmed.themoviedb.data.architecture.mapData
import com.alaishat.ahmed.themoviedb.data.model.CreditDataModel
import com.alaishat.ahmed.themoviedb.data.model.GenreDataModel
import com.alaishat.ahmed.themoviedb.data.model.MovieDataModel
import com.alaishat.ahmed.themoviedb.data.model.MovieDetailsDataModel
import com.alaishat.ahmed.themoviedb.data.model.ReviewDataModel
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.datasource.remote.paging.defaultPagerOf
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.mapper.mapToGenreDataModels
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.mapper.toDataModel
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.mapper.toEntity
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.mapper.toMovieDataModel
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.mapper.toMovieDetailsDataModel
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.mapper.toReviewDataModel
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.model.MovieListTypeDataModel
import com.alaishat.ahmed.themoviedb.datasource.source.local.LocalMoviesDataSource
import comalaishatahmedthemoviedbdatasourceimplsqldelight.CreditEntity
import comalaishatahmedthemoviedbdatasourceimplsqldelight.MovieEntity
import comalaishatahmedthemoviedbdatasourceimplsqldelight.SelectMoviesReviewsPage
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
        movies: List<MovieDataModel>
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

    override fun getCachedMovieList(movieListTypeDataModel: MovieListTypeDataModel): List<MovieDataModel> {
        return queriesProvider.movie.selectMoviesListByType(
            type = movieListTypeDataModel,
        ).executeAsList().map(MovieEntity::toMovieDataModel)
    }

    override fun searchCachedMovieList(query: String): List<MovieDataModel> {
        return queriesProvider.movie.searchMoviesList(
            query = query,
        ).executeAsList().map(MovieEntity::toMovieDataModel)
    }

    override fun getCachedMoviesPagingFlow(movieListTypeDataModel: MovieListTypeDataModel): Flow<PagingData<MovieDataModel>> {
        val pager = defaultPagerOf(
            pagingSourceFactory = {
                QueryPagingSource(
                    countQuery = queriesProvider.movie.selectMoviesCountByType(movieListTypeDataModel),
                    transacter = queriesProvider.movie,
                    context = ioDispatcher,
                    queryProvider = { limit, offset ->
                        queriesProvider.movie.selectMoviesPageByType(
                            type = movieListTypeDataModel,
                            limit = limit,
                            offset = offset,
                        )
                    },
                )
            }
        )
        return pager.flow.mapData(MovieEntity::toMovieDataModel)
    }

    override fun cacheMovieReviews(movieId: Int, reviews: List<ReviewDataModel>) {
        queriesProvider.review.transaction {
            reviews.forEach { review ->
                queriesProvider.author.upsertAuthor(review.authorDetailsDataModel.toEntity())
                queriesProvider.review.upsertReview(review.toEntity(movieId = movieId))
            }
        }
    }

    override fun getCachedReviewsPagingFlow(movieId: Int): Flow<PagingData<ReviewDataModel>> {
        val pager = defaultPagerOf(
            pagingSourceFactory = {
                QueryPagingSource(
                    countQuery = queriesProvider.review.selectMovieReviewsCount(movieId = movieId.toLong()),
                    transacter = queriesProvider.review,
                    context = ioDispatcher,
                    queryProvider = { limit, offset ->
                        queriesProvider.review.selectMoviesReviewsPage(
                            movieId = movieId.toLong(),
                            limit = limit,
                            offset = offset,
                        )
                    },
                )
            }
        )
        return pager.flow.mapData(SelectMoviesReviewsPage::toReviewDataModel)
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

    override fun cacheMovieCredits(movieId: Int, credits: List<CreditDataModel>) {
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