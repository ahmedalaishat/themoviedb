package com.alaishat.ahmed.themoviedb.initializer

import android.content.Context
import androidx.startup.Initializer
import com.alaishat.ahmed.themoviedb.BuildConfig
import timber.log.Timber

/**
 * Created by Ahmed Al-Aishat on Jun/24/2023.
 * The Movie DB Project.
 */
class TimberInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Timber.d("TimberInitializer is initialized.")
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}