package com.alaishat.ahmed.themoviedb.di

import com.alaishat.ahmed.themoviedb.data.repository.AccountRepositoryImpl
import com.alaishat.ahmed.themoviedb.data.repository.MoviesRepositoryImpl
import com.alaishat.ahmed.themoviedb.domain.repository.AccountRepository
import com.alaishat.ahmed.themoviedb.domain.repository.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindsAccountRepository(
        accountRepositoryImpl: AccountRepositoryImpl,
    ): AccountRepository

    @Binds
    @Singleton
    fun bindsMoviesRepository(
        movieListRepository: MoviesRepositoryImpl,
    ): MoviesRepository
}