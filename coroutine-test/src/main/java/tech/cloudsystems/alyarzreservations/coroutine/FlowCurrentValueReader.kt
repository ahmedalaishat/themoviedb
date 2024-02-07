package tech.cloudsystems.alyarzreservations.coroutine

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest

/**
 * Created by Ahmed Al-Aishat on Oct/23/2023.
 * The Movie DB Project.
 */
suspend fun <VALUE_TYPE> Flow<VALUE_TYPE>.currentValue() =
    take(1).toList().first()