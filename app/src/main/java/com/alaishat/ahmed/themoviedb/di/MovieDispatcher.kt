package com.alaishat.ahmed.themoviedb.di

import javax.inject.Qualifier

/**
 * Created by Ahmed Al-Aishat on Jun/24/2023.
 * The Movie DB Project.
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val movieDispatcher: MovieDispatcher)

enum class MovieDispatcher {
    IO,
}
