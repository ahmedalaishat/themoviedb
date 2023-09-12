package com.alaishat.ahmed.themoviedb.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.alaishat.ahmed.themoviedb.data.model.mapToReviewsDataModels
import com.alaishat.ahmed.themoviedb.datasource.source.network.MoviesDataSource
import com.alaishat.ahmed.themoviedb.domain.model.ReviewDomainModel

/**
 * Created by Ahmed Al-Aishat on Sep/09/2023.
 * The Movie DB Project.
 */
class ReviewsPagingSource(
    private val moviesDataSource: MoviesDataSource,
    private val movieId: Int,
) : PagingSource<Int, ReviewDomainModel>() {
    override fun getRefreshKey(state: PagingState<Int, ReviewDomainModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ReviewDomainModel> {
        return try {
            val page = params.key ?: 1
            // calling the paging api
            val fetchedMovies = moviesDataSource.getMovieReviews(movieId, page).mapToReviewsDataModels()

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