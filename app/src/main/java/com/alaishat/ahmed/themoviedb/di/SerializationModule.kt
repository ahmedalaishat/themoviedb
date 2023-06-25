package com.alaishat.ahmed.themoviedb.di

import com.alaishat.ahmed.themoviedb.data.source.network.NetworkMovieListsDataSource
import com.alaishat.ahmed.themoviedb.network.datasource.KtorMovieListsDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
@Module
@InstallIn(SingletonComponent::class)
object SerializationModule {

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
        prettyPrint = true
        isLenient = true
    }
}