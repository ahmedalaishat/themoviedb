package com.alaishat.ahmed.themoviedb.di

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.EnumColumnAdapter
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.alaishat.ahmed.themoviedb.TheMovieDBDatabase
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.datasource.local.DelightLocalMovieDataSource
import com.alaishat.ahmed.themoviedb.datasource.impl.movie.datasource.local.MovieQueriesProvider
import com.alaishat.ahmed.themoviedb.datasource.source.local.LocalMoviesDataSource
import comalaishatahmedthemoviedbdatasourceimplsqldelight.AuthorEntityQueries
import comalaishatahmedthemoviedbdatasourceimplsqldelight.CreditEntityQueries
import comalaishatahmedthemoviedbdatasourceimplsqldelight.GenreEntityQueries
import comalaishatahmedthemoviedbdatasourceimplsqldelight.GenreMovieEntityQueries
import comalaishatahmedthemoviedbdatasourceimplsqldelight.MovieDetailsEntityQueries
import comalaishatahmedthemoviedbdatasourceimplsqldelight.MovieEntityQueries
import comalaishatahmedthemoviedbdatasourceimplsqldelight.ReviewEntityQueries
import comalaishatahmedthemoviedbdatasourceimplsqldelight.TypeMovieEntity
import comalaishatahmedthemoviedbdatasourceimplsqldelight.TypeMovieEntityQueries
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

/**
 * Created by Ahmed Al-Aishat on Sep/12/2023.
 * The Movie DB Project.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

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
            TypeMovieEntityAdapter = TypeMovieEntity.Adapter(
                typeAdapter = EnumColumnAdapter(),
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
    fun provideTypeMovieDataSource(db: TheMovieDBDatabase): TypeMovieEntityQueries {
        return db.typeMovieEntityQueries
    }

    @Provides
    @Singleton
    fun provideGenreDataSource(db: TheMovieDBDatabase): GenreEntityQueries {
        return db.genreEntityQueries
    }

    @Provides
    @Singleton
    fun provideReviewDataSource(db: TheMovieDBDatabase): ReviewEntityQueries {
        return db.reviewEntityQueries
    }

    @Provides
    @Singleton
    fun provideAuthorDataSource(db: TheMovieDBDatabase): AuthorEntityQueries {
        return db.authorEntityQueries
    }

    @Provides
    @Singleton
    fun provideCreditDataSource(db: TheMovieDBDatabase): CreditEntityQueries {
        return db.creditEntityQueries
    }

    @Provides
    @Singleton
    fun provideMovieDetailsDataSource(db: TheMovieDBDatabase): MovieDetailsEntityQueries {
        return db.movieDetailsEntityQueries
    }

    @Provides
    @Singleton
    fun provideGenreMovieDataSource(db: TheMovieDBDatabase): GenreMovieEntityQueries {
        return db.genreMovieEntityQueries
    }


    @Provides
    @Singleton
    fun providesMovieQueriesProvider(
        movie: MovieEntityQueries,
        movieDetails: MovieDetailsEntityQueries,
        typeMovie: TypeMovieEntityQueries,
        genre: GenreEntityQueries,
        genreMovie: GenreMovieEntityQueries,
        review: ReviewEntityQueries,
        author: AuthorEntityQueries,
        credit: CreditEntityQueries,
    ): MovieQueriesProvider =
        MovieQueriesProvider(
            movie = movie,
            movieDetails = movieDetails,
            typeMovie = typeMovie,
            genre = genre,
            genreMovie = genreMovie,
            review = review,
            author = author,
            credit = credit,
        )

    @Provides
    @Singleton
    fun providesLocalMoviesDataSource(
        movieQueriesProvider: MovieQueriesProvider,
        @Dispatcher(AppDispatchers.IO) ioDispatcher: CoroutineDispatcher,
    ): LocalMoviesDataSource =
        DelightLocalMovieDataSource(
            queriesProvider = movieQueriesProvider,
            ioDispatcher = ioDispatcher,
        )
}