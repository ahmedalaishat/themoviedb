package com.alaishat.ahmed.themoviedb.domain

import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import com.alaishat.ahmed.themoviedb.domain.model.Movie
import com.alaishat.ahmed.themoviedb.domain.repository.MoviesRepository
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
    private val moviesRepository: MoviesRepository,
) {
    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    operator fun invoke(queryFlow: Flow<String>): Flow<PagingData<Movie>> {
        val emptyPagingData = PagingData.empty<Movie>(
            sourceLoadStates = LoadStates(
                refresh = LoadState.Loading,
                prepend = LoadState.NotLoading(false),
                append = LoadState.NotLoading(false),
            )
        )

        return queryFlow
            .debounce(DEBOUNCE_TIMEOUT)
            .flatMapLatest {
                if (it.isEmpty()) flowOf(emptyPagingData)
                else moviesRepository.getSearchMoviePagingFlow(it)
                    .catch { emitAll(flowOf()) }
            }
    }
}