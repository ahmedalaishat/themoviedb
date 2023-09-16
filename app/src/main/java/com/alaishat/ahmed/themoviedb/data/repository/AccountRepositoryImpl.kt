package com.alaishat.ahmed.themoviedb.data.repository

import androidx.paging.PagingData
import com.alaishat.ahmed.themoviedb.data.architecture.mapData
import com.alaishat.ahmed.themoviedb.data.model.MovieDataModel
import com.alaishat.ahmed.themoviedb.data.model.toMovieDomainModel
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.model.MovieListTypeDataModel
import com.alaishat.ahmed.themoviedb.datasource.source.local.LocalMoviesDataSource
import com.alaishat.ahmed.themoviedb.datasource.source.network.RemoteAccountDataSource
import com.alaishat.ahmed.themoviedb.domain.model.MovieDomainModel
import com.alaishat.ahmed.themoviedb.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Ahmed Al-Aishat on Sep/11/2023.
 * The Movie DB Project.
 */
class AccountRepositoryImpl @Inject constructor(
    private val remoteAccountDataSource: RemoteAccountDataSource,
    private val localMoviesDataSource: LocalMoviesDataSource,
) : AccountRepository {

    override fun getWatchListPagingFlow(): Flow<PagingData<MovieDomainModel>> {
        val pagingFlow = remoteAccountDataSource.getWatchlistPagingFlow(
            pageCachingHandler = { page, movies ->
                localMoviesDataSource.cacheMovieList(
                    deleteCached = page == 1,
                    movieListTypeDataModel = MovieListTypeDataModel.WATCHLIST,
                    movies = movies,
                )
            }
        )
        return pagingFlow.mapData(MovieDataModel::toMovieDomainModel)
    }

    override suspend fun toggleWatchlistMovie(movieId: Int, watchlist: Boolean) {
        remoteAccountDataSource.toggleWatchlistMovie(movieId = movieId, watchlist = watchlist)
    }
}