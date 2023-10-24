package com.alaishat.ahmed.themoviedb.domain.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

/**
 * Created by Ahmed Al-Aishat on Sep/16/2023.
 * The Movie DB Project.
 */
interface BackgroundExecutor {
    val ioDispatcher: CoroutineDispatcher

    suspend fun <T> doInBackground(block: suspend CoroutineScope.() -> T) = withContext(ioDispatcher, block)

    fun <T> Flow<T>.flowOnBackground() = flowOn(ioDispatcher)
}