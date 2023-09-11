package com.alaishat.ahmed.themoviedb.di

import com.alaishat.ahmed.themoviedb.data.source.network.AccountDataSource
import com.alaishat.ahmed.themoviedb.data.source.network.MoviesDataSource
import com.alaishat.ahmed.themoviedb.network.datasource.KtorAccountDataSource
import com.alaishat.ahmed.themoviedb.network.datasource.KtorMoviesDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by Ahmed Al-Aishat on Jun/24/2023.
 * The Movie DB Project.
 */
@Module
@InstallIn(SingletonComponent::class)
interface NetworkModule {

    @Binds
    fun providesAccountDataSource(
        accountDataSource: KtorAccountDataSource,
    ): AccountDataSource

    @Binds
    fun providesMoviesDataSource(
        moviesDataSource: KtorMoviesDataSource,
    ): MoviesDataSource
}