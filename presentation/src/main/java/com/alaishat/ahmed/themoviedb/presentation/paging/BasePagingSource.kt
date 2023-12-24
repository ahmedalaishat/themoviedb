package com.alaishat.ahmed.themoviedb.presentation.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState

/**
 * Created by Ahmed Al-Aishat on Sep/15/2023.
 * The Movie DB Project.
 */
abstract class BasePagingSource<Value : Any>(
    protected open val pageDataProvider: suspend (page: Int) -> List<Value>,
) : PagingSource<Int, Value>() {
    override fun getRefreshKey(state: PagingState<Int, Value>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Value> {
        return try {
            val page = params.key ?: 1
            val fetchedData = pageDataProvider(page)

            resultOf(page = page, fetchedPage = fetchedData)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    protected fun resultOf(
        page: Int,
        fetchedPage: List<Value>,
    ): LoadResult.Page<Int, Value> = LoadResult.Page(
        data = fetchedPage,
        prevKey = if (page > 1) page.minus(1) else null,
        nextKey = if (fetchedPage.isNotEmpty()) page.plus(1) else null,
    )
}