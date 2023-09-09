package com.alaishat.ahmed.themoviedb.network.datasource

import com.alaishat.ahmed.themoviedb.data.model.NetworkCredit
import com.alaishat.ahmed.themoviedb.data.model.NetworkMovie
import com.alaishat.ahmed.themoviedb.data.model.NetworkMovieDetails
import com.alaishat.ahmed.themoviedb.data.model.NetworkReview
import com.alaishat.ahmed.themoviedb.data.source.network.NetworkMoviesDataSource
import com.alaishat.ahmed.themoviedb.network.KtorClient
import com.alaishat.ahmed.themoviedb.network.model.MovieCreditsRes
import com.alaishat.ahmed.themoviedb.network.model.MovieListRes
import com.alaishat.ahmed.themoviedb.network.model.MovieReviewsRes
import io.ktor.client.request.get
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
@Singleton
class KtorMoviesDataSource @Inject constructor(
    private val ktorClient: KtorClient,
) : NetworkMoviesDataSource {

    override suspend fun getMoviesPage(movieListPath: String, page: Int): List<NetworkMovie> {
        val res: MovieListRes = ktorClient.call {
            get("movie/$movieListPath?page=$page&without_keywords=158718")
        }
        return res.results
    }

    override suspend fun searchMovie(query: String, page: Int): List<NetworkMovie> {
        val res: MovieListRes = ktorClient.call {
            get("search/movie?query=$query&page=$page")
        }
        return res.results
    }

    override suspend fun getWatchList(page: Int): List<NetworkMovie> {
        val res: MovieListRes = ktorClient.call {
            get("account/9712119/watchlist/movies?page=$page")
        }
        return res.results
    }

    override suspend fun getMovieDetails(movieId: Int): NetworkMovieDetails {
        val res: NetworkMovieDetails = ktorClient.call {
            get("movie/$movieId")
        }
        return res
    }

    override suspend fun getMovieCredits(movieId: Int): List<NetworkCredit> {
        val res: MovieCreditsRes = ktorClient.call {
            get("movie/$movieId/credits")
        }
        return res.cast
    }

    override suspend fun getMovieReviews(movieId: Int, page: Int): List<NetworkReview> {
        val res: MovieReviewsRes = ktorClient.call {
            get("movie/$movieId/reviews?page=$page")
        }
        return res.reviews
    }
}