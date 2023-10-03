package com.alaishat.ahmed.themoviedb.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

/**
 * Created by Ahmed Al-Aishat on Jun/24/2023.
 * The Movie DB Project.
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val appDispatchers: AppDispatchers)

enum class AppDispatchers {
    DEFAULT, IO, MAIN, IMMEDIATE,
}

@Module
@InstallIn(SingletonComponent::class)
object CoroutineDispatchersModule {
    @Provides
    @Dispatcher(AppDispatchers.DEFAULT)
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @Dispatcher(AppDispatchers.IO)
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Dispatcher(AppDispatchers.MAIN)
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @Dispatcher(AppDispatchers.IMMEDIATE)
    fun providesImmediateDispatcher(): CoroutineDispatcher = Dispatchers.Main.immediate
}