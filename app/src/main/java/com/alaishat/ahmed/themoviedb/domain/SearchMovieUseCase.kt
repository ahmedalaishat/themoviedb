package com.alaishat.ahmed.themoviedb.domain

import com.alaishat.ahmed.themoviedb.domain.model.Movie
import com.alaishat.ahmed.themoviedb.domain.repository.MovieListRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
private const val DEBOUNCE_TIMEOUT = 300L

class SearchMovieUseCase @Inject constructor(
    private val movieListRepository: MovieListRepository,
) {
    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    operator fun invoke(queryFlow: Flow<String>): Flow<List<Movie>> {
        return queryFlow
            .debounce(DEBOUNCE_TIMEOUT)
            .flatMapLatest {
                if (it.isEmpty()) flowOf(emptyList())
                else movieListRepository.searchMovie(it)
                    .catch { emitAll(flowOf()) }
            }
    }
}