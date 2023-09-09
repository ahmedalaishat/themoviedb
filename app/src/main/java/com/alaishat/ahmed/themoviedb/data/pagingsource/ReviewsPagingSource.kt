package com.alaishat.ahmed.themoviedb.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.alaishat.ahmed.themoviedb.data.model.mapToReviews
import com.alaishat.ahmed.themoviedb.data.source.network.NetworkMoviesDataSource
import com.alaishat.ahmed.themoviedb.domain.model.Review

/**
 * Created by Ahmed Al-Aishat on Sep/09/2023.
 * The Movie DB Project.
 */
class ReviewsPagingSource(
    private val moviesDataSource: NetworkMoviesDataSource,
    private val movieId: Int,
) : PagingSource<Int, Review>() {
    override fun getRefreshKey(state: PagingState<Int, Review>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Review> {
        return try {
            val page = params.key ?: 1
            // calling the paging api
            val fetchedMovies = moviesDataSource.getMovieReviews(movieId, page).mapToReviews()

            LoadResult.Page(
                data = fetchedMovies,
                prevKey = if (page > 1) page.minus(1) else null,
                nextKey = if (fetchedMovies.isNotEmpty()) page.plus(1) else null,
            )
        } catch (e: Exception) {
            PagingSource.LoadResult.Error(e)
        }
    }
}