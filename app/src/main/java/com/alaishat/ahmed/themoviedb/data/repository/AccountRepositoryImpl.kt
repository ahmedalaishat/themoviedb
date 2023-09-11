package com.alaishat.ahmed.themoviedb.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.alaishat.ahmed.themoviedb.data.pagingsource.WatchListPagingSource
import com.alaishat.ahmed.themoviedb.data.source.network.AccountDataSource
import com.alaishat.ahmed.themoviedb.data.source.network.MoviesDataSource
import com.alaishat.ahmed.themoviedb.domain.model.Movie
import com.alaishat.ahmed.themoviedb.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Ahmed Al-Aishat on Sep/11/2023.
 * The Movie DB Project.
 */
class AccountRepositoryImpl @Inject constructor(
    private val accountDataSource: AccountDataSource,
) : AccountRepository {

    override fun getWatchListPagingFlow(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = DEFAULT_PAGE_SIZE),
            pagingSourceFactory = {
                WatchListPagingSource(
                    accountDataSource = accountDataSource,
                )
            }
        ).flow
    }

    override suspend fun toggleWatchlistMovie(movieId: Int, watchlist: Boolean) {
        accountDataSource.toggleWatchlistMovie(movieId = movieId, watchlist = watchlist)
    }
}