package com.alaishat.ahmed.themoviedb.data.architecture

import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by Ahmed Al-Aishat on Sep/15/2023.
 * The Movie DB Project.
 */
fun <T : Any, R : Any> Flow<PagingData<T>>.mapData(
    transform: suspend (T) -> R
) = map { pagingData ->
    pagingData.map(transform = transform)
}