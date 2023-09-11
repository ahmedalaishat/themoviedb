package com.alaishat.ahmed.themoviedb.network.datasource

import com.alaishat.ahmed.themoviedb.data.model.NetworkMovie
import com.alaishat.ahmed.themoviedb.data.source.network.AccountDataSource
import com.alaishat.ahmed.themoviedb.network.KtorClient
import com.alaishat.ahmed.themoviedb.network.model.MovieListRes
import com.alaishat.ahmed.themoviedb.network.model.ToggleWatchlistMovieReq
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Ahmed Al-Aishat on Sep/11/2023.
 * The Movie DB Project.
 */
const val ACCOUNT_ID = 9712119

@Singleton
class KtorAccountDataSource @Inject constructor(
    private val ktorClient: KtorClient,
) : AccountDataSource {

    override suspend fun getWatchList(page: Int): List<NetworkMovie> {
        val res: MovieListRes = ktorClient.call {
            get("account/$ACCOUNT_ID/watchlist/movies?page=$page")
        }
        return res.results
    }

    override suspend fun toggleWatchlistMovie(movieId: Int, watchlist: Boolean) {
        ktorClient.call<Unit> {
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
}