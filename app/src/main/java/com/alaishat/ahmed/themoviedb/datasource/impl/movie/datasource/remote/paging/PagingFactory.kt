package com.alaishat.ahmed.themoviedb.datasource.impl.movie.datasource.remote.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource

/**
 * Created by Ahmed Al-Aishat on Sep/15/2023.
 * The Movie DB Project.
 */
private const val DEFAULT_PAGE_SIZE = 20

private val defaultPagingConfig = PagingConfig(pageSize = DEFAULT_PAGE_SIZE)

fun <Value : Any> defaultPagerOf(
    pagingSourceFactory: () -> PagingSource<Int, Value>,
) = Pager(
    config = defaultPagingConfig,
    pagingSourceFactory = pagingSourceFactory
)