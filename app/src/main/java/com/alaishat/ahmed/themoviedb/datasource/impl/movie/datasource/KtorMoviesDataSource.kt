package com.alaishat.ahmed.themoviedb.datasource.impl.movie.datasource

import com.alaishat.ahmed.themoviedb.data.model.MovieAccountStatusDataModel
import com.alaishat.ahmed.themoviedb.data.model.CreditDataModel
import com.alaishat.ahmed.themoviedb.data.model.MovieDataModel
import com.alaishat.ahmed.themoviedb.data.model.MovieDetailsDataModel
import com.alaishat.ahmed.themoviedb.data.model.ReviewDataModel
import com.alaishat.ahmed.themoviedb.datasource.source.network.MoviesDataSource
import com.alaishat.ahmed.themoviedb.datasource.impl.remote.KtorClient
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.model.NetworkMovieAccountStatus
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.model.MovieCreditsRes
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.model.MovieListRes
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.model.MovieRatingReq
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.model.MovieReviewsRes
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.model.NetworkMovieDetails
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.model.mapToCreditsDataModels
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.model.mapToMoviesDataModel
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.model.mapToReviewsDataModels
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.model.toMovieAccountStatusDataModel
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.model.toMoviesDetailsDataModel
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
@Singleton
class KtorMoviesDataSource @Inject constructor(
    private val ktorClient: KtorClient,
) : MoviesDataSource {

    override suspend fun getMoviesPage(movieListPath: String, page: Int): List<MovieDataModel> {
        val res: MovieListRes = ktorClient.call {
            get("movie/$movieListPath?page=$page&without_keywords=158718")
        }
        return res.results.mapToMoviesDataModel()
    }

    override suspend fun searchMovie(query: String, page: Int): List<MovieDataModel> {
        val res: MovieListRes = ktorClient.call {
            get("search/movie?query=$query&page=$page")
        }
        return res.results.mapToMoviesDataModel()
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
}