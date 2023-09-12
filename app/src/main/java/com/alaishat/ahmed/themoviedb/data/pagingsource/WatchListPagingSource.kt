package com.alaishat.ahmed.themoviedb.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.alaishat.ahmed.themoviedb.data.model.mapToMovies
import com.alaishat.ahmed.themoviedb.datasource.source.network.AccountDataSource
import com.alaishat.ahmed.themoviedb.domain.model.MovieDomainModel

/**
 * Created by Ahmed Al-Aishat on Jun/28/2023 (Eid Al-Adha night 😁).
 * The Movie DB Project.
 */
class WatchListPagingSource(
    private val accountDataSource: AccountDataSource,
) : PagingSource<Int, MovieDomainModel>() {
    override fun getRefreshKey(state: PagingState<Int, MovieDomainModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDomainModel> {
        return try {
            val page = params.key ?: 1
            // calling the paging api
            val fetchedMovies = accountDataSource.getWatchList(page).mapToMovies()

            LoadResult.Page(
                data = fetchedMovies,
                prevKey = if (page > 1) page.minus(1) else null,
                nextKey = if (fetchedMovies.isNotEmpty()) page.plus(1) else null,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}