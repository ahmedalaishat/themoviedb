package com.alaishat.ahmed.themoviedb.datasource.impl.movie.datasource.local

import comalaishatahmedthemoviedbdatasourceimplsqldelight.AuthorEntityQueries
import comalaishatahmedthemoviedbdatasourceimplsqldelight.CreditEntityQueries
import comalaishatahmedthemoviedbdatasourceimplsqldelight.GenreEntityQueries
import comalaishatahmedthemoviedbdatasourceimplsqldelight.GenreMovieEntityQueries
import comalaishatahmedthemoviedbdatasourceimplsqldelight.MovieDetailsEntityQueries
import comalaishatahmedthemoviedbdatasourceimplsqldelight.MovieEntityQueries
import comalaishatahmedthemoviedbdatasourceimplsqldelight.ReviewEntityQueries
import comalaishatahmedthemoviedbdatasourceimplsqldelight.TypeMovieEntityQueries

/**
 * Created by Ahmed Al-Aishat on Oct/04/2023.
 * The Movie DB Project.
 */
class MovieQueriesProvider(
    val movie: MovieEntityQueries,
    val movieDetails: MovieDetailsEntityQueries,
    val typeMovie: TypeMovieEntityQueries,
    val genre: GenreEntityQueries,
    val genreMovie: GenreMovieEntityQueries,
    val review: ReviewEntityQueries,
    val author: AuthorEntityQueries,
    val credit: CreditEntityQueries,
)