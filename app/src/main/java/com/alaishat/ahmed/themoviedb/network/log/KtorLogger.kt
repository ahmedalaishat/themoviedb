package com.alaishat.ahmed.themoviedb.network.log

import io.ktor.client.plugins.logging.Logger
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Ahmed Al-Aishat on Jun/24/2023.
 * The Movie DB Project.
 */
@Singleton
class KtorLogger @Inject constructor() : Logger {
    override fun log(message: String) = Timber.tag("Ktor Logger:").v(message)
}