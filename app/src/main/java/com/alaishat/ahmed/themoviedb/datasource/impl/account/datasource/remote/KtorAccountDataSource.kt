package com.alaishat.ahmed.themoviedb.datasource.impl.account.datasource.remote

import androidx.paging.PagingData
import com.alaishat.ahmed.themoviedb.data.model.MovieDataModel
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.datasource.remote.paging.CacheablePagingSource
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.datasource.remote.paging.defaultPagerOf
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.model.MovieListRes
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.model.ToggleWatchlistMovieReq
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.model.mapToMoviesDataModel
import com.alaishat.ahmed.themoviedb.datasource.impl.remote.KtorClient
import com.alaishat.ahmed.themoviedb.datasource.source.network.RemoteAccountDataSource
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.Flow
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
) : RemoteAccountDataSource {

    override suspend fun getWatchlist(page: Int): List<MovieDataModel> {
        val res: MovieListRes = ktorClient.call {
            get("account/$ACCOUNT_ID/watchlist/movies?page=$page")
        }
        return res.results.mapToMoviesDataModel()
    }

    override fun getWatchlistPagingFlow(
        pageCachingHandler: suspend (page: Int, pageData: List<MovieDataModel>) -> Unit,
    ): Flow<PagingData<MovieDataModel>> {
        val pager = defaultPagerOf(
            pagingSourceFactory = {
                CacheablePagingSource(
                    pageDataProvider = { page ->
                        getWatchlist(page)
                    },
                    pageCachingHandler = pageCachingHandler
                )
            }
        )
        return pager.flow
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