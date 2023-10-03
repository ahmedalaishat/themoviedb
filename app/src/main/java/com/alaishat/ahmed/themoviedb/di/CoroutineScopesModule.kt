package com.alaishat.ahmed.themoviedb.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

/**
 * Created by Ahmed Al-Aishat on Sep/16/2023.
 * The Movie DB Project.
 */
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope

@Module
@InstallIn(SingletonComponent::class)
object CoroutineScopesModule {

    @Singleton
    @ApplicationScope
    @Provides
    fun providesCoroutineScope(
        @Dispatcher(AppDispatchers.DEFAULT) defaultDispatcher: CoroutineDispatcher
    ): CoroutineScope {
        return CoroutineScope(SupervisorJob() + defaultDispatcher)
    }
}