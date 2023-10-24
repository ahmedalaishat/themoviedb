package com.alaishat.ahmed.themoviedb.datasource.local.provider

import comalaishatahmedthemoviedbdatasourcesqldelight.AuthorEntityQueries
import comalaishatahmedthemoviedbdatasourcesqldelight.CreditEntityQueries
import comalaishatahmedthemoviedbdatasourcesqldelight.GenreEntityQueries
import comalaishatahmedthemoviedbdatasourcesqldelight.GenreMovieEntityQueries
import comalaishatahmedthemoviedbdatasourcesqldelight.MovieDetailsEntityQueries
import comalaishatahmedthemoviedbdatasourcesqldelight.MovieEntityQueries
import comalaishatahmedthemoviedbdatasourcesqldelight.ReviewEntityQueries
import comalaishatahmedthemoviedbdatasourcesqldelight.TypeMovieEntityQueries

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