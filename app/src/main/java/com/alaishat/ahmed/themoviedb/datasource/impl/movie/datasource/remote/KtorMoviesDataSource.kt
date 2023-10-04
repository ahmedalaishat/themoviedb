package com.alaishat.ahmed.themoviedb.datasource.impl.movie.datasource.remote

import androidx.paging.PagingData
import com.alaishat.ahmed.themoviedb.data.model.CreditDataModel
import com.alaishat.ahmed.themoviedb.data.model.GenreDataModel
import com.alaishat.ahmed.themoviedb.data.model.MovieAccountStatusDataModel
import com.alaishat.ahmed.themoviedb.data.model.MovieDataModel
import com.alaishat.ahmed.themoviedb.data.model.MovieDetailsDataModel
import com.alaishat.ahmed.themoviedb.data.model.ReviewDataModel
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.datasource.remote.paging.CacheablePagingSource
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.datasource.remote.paging.NormalPagingSource
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.datasource.remote.paging.defaultPagerOf
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.model.MovieCreditsRes
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.model.MovieGenreListRes
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.model.MovieListRes
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.model.MovieListTypeDataModel
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.model.MovieRatingReq
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.model.MovieReviewsRes
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.model.NetworkMovieAccountStatus
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.model.NetworkMovieDetails
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.model.mapToCreditsDataModels
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.model.mapToGenresDataModels
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.model.mapToMoviesDataModel
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.model.mapToReviewsDataModels
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.model.toMovieAccountStatusDataModel
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.model.toMoviesDetailsDataModel
import com.alaishat.ahmed.themoviedb.datasource.impl.remote.KtorClient
import com.alaishat.ahmed.themoviedb.datasource.source.remote.RemoteMoviesDataSource
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
class KtorMoviesDataSource(
    private val ktorClient: KtorClient,
) : RemoteMoviesDataSource {

    override suspend fun getMoviesPage(
        movieListTypeDataModel: MovieListTypeDataModel,
        page: Int
    ): List<MovieDataModel> {
        val res: MovieListRes = ktorClient.call {
            get("movie/${movieListTypeDataModel.listApiPath}?page=$page")
//            &without_keywords=158718
        }
        return res.results.mapToMoviesDataModel()
    }

    override fun getCacheableMoviesPagingFlow(
        movieListTypeDataModel: MovieListTypeDataModel,
        pageCachingHandler: suspend (page: Int, pageData: List<MovieDataModel>) -> Unit
    ): Flow<PagingData<MovieDataModel>> {
        val pager = defaultPagerOf(
            pagingSourceFactory = {
                CacheablePagingSource(
                    pageDataProvider = { page ->
                        getMoviesPage(movieListTypeDataModel = movieListTypeDataModel, page = page)
                    },
                    pageCachingHandler = pageCachingHandler,
                )
            })
        return pager.flow
    }

    override suspend fun searchMovie(query: String, page: Int): List<MovieDataModel> {
        val res: MovieListRes = ktorClient.call {
            get("search/movie?query=$query&page=$page")
        }
        return res.results.mapToMoviesDataModel()
    }

    override fun getSearchMoviePagingFlow(query: String): Flow<PagingData<MovieDataModel>> {
        val pager = defaultPagerOf(
            pagingSourceFactory = {
                NormalPagingSource(
                    pageDataProvider = { page ->
                        searchMovie(query, page)
                    },
                )
            })
        return pager.flow
    }

    override suspend fun getMovieDetails(movieId: Int): MovieDetailsDataModel {
        val res: NetworkMovieDetails = ktorClient.call {
            get("movie/$movieId")
        }
        return res.toMoviesDetailsDataModel()
    }

    override suspend fun getMovieCredits(movieId: Int): List<CreditDataModel> {
        val res: MovieCreditsRes = ktorClient.call {
            get("movie/$movieId/credits")
        }
        return res.cast.mapToCreditsDataModels()
    }

    override suspend fun getMovieReviews(movieId: Int, page: Int): List<ReviewDataModel> {
        val res: MovieReviewsRes = ktorClient.call {
            get("movie/$movieId/reviews?page=$page")
        }
        return res.reviews.mapToReviewsDataModels()
    }

    override fun getMovieReviewsPagingFlow(
        movieId: Int,
        pageCachingHandler: suspend (page: Int, pageData: List<ReviewDataModel>) -> Unit
    ): Flow<PagingData<ReviewDataModel>> {
        return defaultPagerOf(
            pagingSourceFactory = {
                CacheablePagingSource(
                    pageDataProvider = { page ->
                        getMovieReviews(movieId = movieId, page = page)
                    },
                    pageCachingHandler = pageCachingHandler
                )
            }
        ).flow
    }

    override suspend fun addMovieRating(movieId: Int, rating: Int) {
        ktorClient.call<Unit> {
            post("movie/$movieId/rating") {
                setBody(body = MovieRatingReq(value = rating))
            }
        }
    }

    override suspend fun getMovieAccountStatus(movieId: Int): MovieAccountStatusDataModel {
        val res: NetworkMovieAccountStatus = ktorClient.call {
            get("movie/$movieId/account_states")
        }
        return res.toMovieAccountStatusDataModel()
    }

    override suspend fun getMovieGenreList(): List<GenreDataModel> {
        val res: MovieGenreListRes = ktorClient.call {
            get("genre/movie/list")
        }
        return res.genres.mapToGenresDataModels()
    }
}