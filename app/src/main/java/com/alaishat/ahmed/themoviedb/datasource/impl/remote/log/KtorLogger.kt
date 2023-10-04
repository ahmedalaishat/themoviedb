package com.alaishat.ahmed.themoviedb.datasource.impl.remote.log

import io.ktor.client.plugins.logging.Logger
import timber.log.Timber

/**
 * Created by Ahmed Al-Aishat on Jun/24/2023.
 * The Movie DB Project.
 */
class KtorLogger : Logger {
    override fun log(message: String) = Timber.tag("Ktor Logger:").v(message)
}