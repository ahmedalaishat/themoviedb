package com.alaishat.ahmed.themoviedb.datasource.impl.movie.datasource.local

import androidx.paging.PagingData
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.paging3.QueryPagingSource
import com.alaishat.ahmed.themoviedb.data.architecture.mapData
import com.alaishat.ahmed.themoviedb.data.model.CreditDataModel
import com.alaishat.ahmed.themoviedb.data.model.GenreDataModel
import com.alaishat.ahmed.themoviedb.data.model.MovieDataModel
import com.alaishat.ahmed.themoviedb.data.model.MovieDetailsDataModel
import com.alaishat.ahmed.themoviedb.data.model.ReviewDataModel
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.datasource.remote.paging.defaultPagerOf
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.mapper.mapToGenreDataModel
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.mapper.toDataModel
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.mapper.toEntity
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.mapper.toMovieDataModel
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.mapper.toMovieDetailsDataModel
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.model.MovieListTypeDataModel
import com.alaishat.ahmed.themoviedb.datasource.source.local.LocalMoviesDataSource
import comalaishatahmedthemoviedbdatasourceimplsqldelight.AuthorEntityQueries
import comalaishatahmedthemoviedbdatasourceimplsqldelight.CreditEntity
import comalaishatahmedthemoviedbdatasourceimplsqldelight.CreditEntityQueries
import comalaishatahmedthemoviedbdatasourceimplsqldelight.GenreEntityQueries
import comalaishatahmedthemoviedbdatasourceimplsqldelight.GenreMovieEntityQueries
import comalaishatahmedthemoviedbdatasourceimplsqldelight.MovieDetailsEntityQueries
import comalaishatahmedthemoviedbdatasourceimplsqldelight.MovieEntity
import comalaishatahmedthemoviedbdatasourceimplsqldelight.MovieEntityQueries
import comalaishatahmedthemoviedbdatasourceimplsqldelight.ReviewEntityQueries
import comalaishatahmedthemoviedbdatasourceimplsqldelight.TypeMovieEntityQueries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Ahmed Al-Aishat on Sep/12/2023.
 * The Movie DB Project.
 */
@Singleton
class DelightLocalMovieDataSource @Inject constructor(
    private val movieEntityQueries: MovieEntityQueries,
    private val movieDetailsEntityQueries: MovieDetailsEntityQueries,
    private val typeMovieEntityQueries: TypeMovieEntityQueries,
    private val genreEntityQueries: GenreEntityQueries,
    private val genreMovieEntityQueries: GenreMovieEntityQueries,
    private val reviewEntityQueries: ReviewEntityQueries,
    private val authorEntityQueries: AuthorEntityQueries,
    private val creditEntityQueries: CreditEntityQueries,
) : LocalMoviesDataSource {

    override fun cacheMovieList(
        movieListTypeDataModel: MovieListTypeDataModel,
        deleteCached: Boolean,
        movies: List<MovieDataModel>
    ) {
        movieEntityQueries.selectAll().executeAsList()
        movies.forEach { movie ->
            movieEntityQueries.upsertMovie(movie.toEntity())
        }
        typeMovieEntityQueries.transaction {
            if (deleteCached)
                typeMovieEntityQueries.deleteListByType(movieListTypeDataModel)
            movies.forEach { movie ->
                typeMovieEntityQueries.insert(type = movieListTypeDataModel, movieId = movie.id.toLong())
            }
        }
    }

    override fun getCachedMovieList(movieListTypeDataModel: MovieListTypeDataModel): List<MovieDataModel> {
        return movieEntityQueries.selectMoviesListByType(
            type = movieListTypeDataModel,
        ).executeAsList().map(MovieEntity::toMovieDataModel)
    }

    override fun getCachedMoviesPagingFlow(movieListTypeDataModel: MovieListTypeDataModel): Flow<PagingData<MovieDataModel>> {
        val pager = defaultPagerOf(
            pagingSourceFactory = {
                QueryPagingSource(
                    countQuery = movieEntityQueries.selectMoviesCountByType(movieListTypeDataModel),
                    transacter = movieEntityQueries,
                    context = Dispatchers.IO,
                    queryProvider = { limit, offset ->
                        movieEntityQueries.selectMoviesPageByType(
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
        reviewEntityQueries.transaction {
            reviews.forEach { review ->
                authorEntityQueries.upsertAuthor(review.authorDetailsDataModel.toEntity())
                reviewEntityQueries.upsertReview(review.toEntity(movieId = movieId))
            }
        }
    }

    override fun getMovieReviewsFlow(movieId: Int): List<ReviewDataModel> {
        return listOf()
//        val a: List<SelectReviewsByMovieId> = reviewEntityQueries.selectReviewsByMovieId(movieId = movieId.toLong()).executeAsList()
    }

    override fun getMovieGenreList(): Flow<List<GenreDataModel>> {
        return genreEntityQueries.selectAll().asFlow().mapToList(Dispatchers.IO).map {
            it.mapToGenreDataModel()
        }
    }

    override fun updateMovieGenreList(genreList: List<GenreDataModel>) {
        genreEntityQueries.transaction {
            genreList.forEach { genre ->
                genreEntityQueries.upsertGenre(genre.toEntity())
            }
        }
    }

    override fun cacheMovieCredits(movieId: Int, credits: List<CreditDataModel>) {
        creditEntityQueries.transaction {
            credits.forEach { credit ->
                creditEntityQueries.upsertCredit(credit.toEntity(movieId = movieId))
            }
        }
    }

    override fun getCachedMovieCredits(movieId: Int): Flow<List<CreditDataModel>> {
        return creditEntityQueries
            .selectMovieCredits(movieId = movieId.toLong())
            .asFlow()
            .map { it.executeAsList() }
            .map {
                it.map(CreditEntity::toDataModel)
            }
    }

    override fun cacheMovieDetails(movieDetailsDataModel: MovieDetailsDataModel) {
        movieDetailsEntityQueries.upsertMovieDetails(movieDetailsDataModel.toEntity())
        genreEntityQueries.transaction {
            movieDetailsDataModel.genreDataModels.forEach { genre ->
                genreEntityQueries.upsertGenre(genre.toEntity())
                genreMovieEntityQueries.upsertGenreMovieDetails(
                    movieId = movieDetailsDataModel.id.toLong(),
                    genreId = genre.id.toLong()
                )
            }
        }
    }

    override fun getCachedMovieDetails(movieId: Int): MovieDetailsDataModel? {
        return movieEntityQueries.selectMovieWithDetailsById(movieId = movieId.toLong()).executeAsList()
            .toMovieDetailsDataModel()
    }

    override fun cacheMovieWatchlistStatus(movieId: Int, watchlist: Boolean) {
        if (watchlist)
            typeMovieEntityQueries.insert(type = MovieListTypeDataModel.WATCHLIST, movieId = movieId.toLong())
        else
            typeMovieEntityQueries.deleteByIdAndType(
                type = MovieListTypeDataModel.WATCHLIST,
                movieId = movieId.toLong()
            )
    }

    override fun observeMovieWatchlistStatus(movieId: Int): Flow<Boolean> {
        return typeMovieEntityQueries.selecctByIdAndType(
            movieId = movieId.toLong(),
            type = MovieListTypeDataModel.WATCHLIST
        ).asFlow().map { it.executeAsOneOrNull() == movieId.toLong() }
    }
}