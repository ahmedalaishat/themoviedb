package com.alaishat.ahmed.themoviedb.di

import com.alaishat.ahmed.themoviedb.data.mapper.MovieDetailsToDomainResolver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by Ahmed Al-Aishat on Sep/16/2023.
 * The Movie DB Project.
 */
@Module
@InstallIn(SingletonComponent::class)
class MovieDetailsModule {

    @Provides
    fun providesMovieDetailsToDomainResolver() =
        MovieDetailsToDomainResolver()

}