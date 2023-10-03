package com.alaishat.ahmed.themoviedb.di

import android.content.Context
import android.net.ConnectivityManager
import com.alaishat.ahmed.themoviedb.datasource.impl.connection.datasource.ConnectionDataSourceImpl
import com.alaishat.ahmed.themoviedb.datasource.source.connection.datasource.ConnectionDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.MainScope

/**
 * Created by Ahmed Al-Aishat on Sep/16/2023.
 * The Movie DB Project.
 */
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun providesConnectivityManager(@ApplicationContext context: Context) =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

}