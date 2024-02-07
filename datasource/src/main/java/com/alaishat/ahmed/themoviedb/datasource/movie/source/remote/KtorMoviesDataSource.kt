package com.alaishat.ahmed.themoviedb.datasource.movie.source.remote

import com.alaishat.ahmed.themoviedb.data.architecture.DataResult
import com.alaishat.ahmed.themoviedb.data.architecture.getOrThrow
import com.alaishat.ahmed.themoviedb.data.architecture.successMapper
import com.alaishat.ahmed.themoviedb.data.model.CreditDataModel
import com.alaishat.ahmed.themoviedb.data.model.GenreDataModel
import com.alaishat.ahmed.themoviedb.data.model.MovieAccountStatusDataModel
import com.alaishat.ahmed.themoviedb.data.model.MovieDataModel
import com.alaishat.ahmed.themoviedb.data.model.MovieDetailsDataModel
import com.alaishat.ahmed.themoviedb.data.model.MovieListTypeDataModel
import com.alaishat.ahmed.themoviedb.data.model.ReviewDataModel
import com.alaishat.ahmed.themoviedb.data.source.remote.RemoteMoviesDataSource
import com.alaishat.ahmed.themoviedb.datasource.constants.ACCOUNT_ID
import com.alaishat.ahmed.themoviedb.datasource.movie.model.MovieCreditsRes
import com.alaishat.ahmed.themoviedb.datasource.movie.model.MovieGenreListRes
import com.alaishat.ahmed.themoviedb.datasource.movie.model.MovieListRes
import com.alaishat.ahmed.themoviedb.datasource.movie.model.MovieRatingReq
import com.alaishat.ahmed.themoviedb.datasource.movie.model.MovieReviewsRes
import com.alaishat.ahmed.themoviedb.datasource.movie.model.NetworkMovieAccountStatus
import com.alaishat.ahmed.themoviedb.datasource.movie.model.NetworkMovieDetails
import com.alaishat.ahmed.themoviedb.datasource.movie.model.ToggleWatchlistMovieReq
import com.alaishat.ahmed.themoviedb.datasource.movie.model.mapToCreditsDataModels
import com.alaishat.ahmed.themoviedb.datasource.movie.model.mapToGenresDataModels
import com.alaishat.ahmed.themoviedb.datasource.movie.model.mapToMoviesDataModel
import com.alaishat.ahmed.themoviedb.datasource.movie.model.mapToReviewsDataModels
import com.alaishat.ahmed.themoviedb.datasource.movie.model.toMovieAccountStatusDataModel
import com.alaishat.ahmed.themoviedb.datasource.movie.model.toMoviesDetailsDataModel
import com.alaishat.ahmed.themoviedb.datasource.remote.KtorClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
class KtorMoviesDataSource(
    private val ktorClient: KtorClient,
) : RemoteMoviesDataSource {

    override suspend fun getMoviesPage(
        movieListTypeDataModel: MovieListTypeDataModel,
        page: Int,
    ): List<MovieDataModel> = ktorClient.safeApiCall<MovieListRes> {
        get("movie/${movieListTypeDataModel.listApiPath}?page=$page")
//            &without_keywords=158718
    }.successMapper {
        it.results.mapToMoviesDataModel()
    }.getOrThrow()

    override suspend fun fetchSearchMoviePage(query: String, page: Int): DataResult<List<MovieDataModel>> =
        ktorClient.safeApiCall<MovieListRes> {
            get("search/movie?query=$query&page=$page")
        }.successMapper {
            it.results.mapToMoviesDataModel()
        }

    override suspend fun getMovieDetails(movieId: Int): DataResult<MovieDetailsDataModel> =
        ktorClient.safeApiCall<NetworkMovieDetails> {
            get("movie/$movieId")
        }.successMapper {
            it.toMoviesDetailsDataModel()
        }

    override suspend fun getMovieCredits(movieId: Int): DataResult<List<CreditDataModel>> =
        ktorClient.safeApiCall<MovieCreditsRes> {
            get("movie/$movieId/credits")
        }.successMapper {
            it.cast.mapToCreditsDataModels()
        }

    override suspend fun getMovieReviewsPage(movieId: Int, page: Int): DataResult<List<ReviewDataModel>> =
        ktorClient.safeApiCall<MovieReviewsRes> {
            get("movie/$movieId/reviews?page=$page")
        }.successMapper {
            it.reviews.mapToReviewsDataModels()
        }

    override suspend fun addMovieRating(movieId: Int, rating: Int) = ktorClient.safeApiCall<Unit> {
        post("movie/$movieId/rating") {
            setBody(body = MovieRatingReq(value = rating))
        }
    }

    override suspend fun getMovieAccountStatus(movieId: Int): DataResult<MovieAccountStatusDataModel> =
        ktorClient.safeApiCall<NetworkMovieAccountStatus> {
            get("movie/$movieId/account_states")
        }.successMapper {
            it.toMovieAccountStatusDataModel()
        }

    override suspend fun getMovieGenreList(): DataResult<List<GenreDataModel>> =
        ktorClient.safeApiCall<MovieGenreListRes> {
            get("genre/movie/list")
        }.successMapper {
            it.genres.mapToGenresDataModels()
        }

    override suspend fun getWatchlistPage(page: Int): DataResult<List<MovieDataModel>> =
        ktorClient.safeApiCall<MovieListRes> {
            get("account/$ACCOUNT_ID/watchlist/movies?page=$page")
        }.successMapper {
            it.results.mapToMoviesDataModel()
        }

    override suspend fun toggleWatchlistMovie(movieId: Int, watchlist: Boolean) = ktorClient.safeApiCall<Unit> {
        post("account/$ACCOUNT_ID/watchlist") {
            setBody(
                body = ToggleWatchlistMovieReq(
                    mediaId = movieId,
                    watchlist = watchlist,
                )
            )
        }
    }
}