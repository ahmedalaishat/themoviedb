package com.alaishat.ahmed.themoviedb.di

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.ColumnAdapter
import app.cash.sqldelight.EnumColumnAdapter
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.alaishat.ahmed.themoviedb.TheMovieDBDatabase
import com.alaishat.ahmed.themoviedb.datasource.impl.local.adapters.listOfIntegersAdapter
import comalaishatahmedthemoviedbdatasourceimplsqldelight.GenreEntityQueries
import comalaishatahmedthemoviedbdatasourceimplsqldelight.MovieEntityQueries
import comalaishatahmedthemoviedbdatasourceimplsqldelight.MovieListEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Ahmed Al-Aishat on Sep/12/2023.
 * The Movie DB Project.
 */
@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideSqlDriver(@ApplicationContext context: Context): SqlDriver {
        return AndroidSqliteDriver(
            schema = TheMovieDBDatabase.Schema,
            context = context,
            name = "the_movie_db.db",
            callback = object : AndroidSqliteDriver.Callback(TheMovieDBDatabase.Schema) {
                override fun onOpen(db: SupportSQLiteDatabase) {
                    db.setForeignKeyConstraintsEnabled(true)
                }
            }
        )
    }

    @Provides
    @Singleton
    fun provideSqlDatabase(driver: SqlDriver): TheMovieDBDatabase {
        return TheMovieDBDatabase(
            driver = driver,
            MovieListEntityAdapter = MovieListEntity.Adapter(
                typeAdapter = EnumColumnAdapter(),
                moviesAdapter = listOfIntegersAdapter
            )
        )
    }

    @Provides
    @Singleton
    fun provideMovieDataSource(db: TheMovieDBDatabase): MovieEntityQueries {
        return db.movieEntityQueries
    }

    @Provides
    @Singleton
    fun provideGenreDataSource(db: TheMovieDBDatabase): GenreEntityQueries {
        return db.genreEntityQueries
    }
}