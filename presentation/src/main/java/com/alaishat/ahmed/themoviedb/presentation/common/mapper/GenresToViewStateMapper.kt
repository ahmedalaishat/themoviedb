package com.alaishat.ahmed.themoviedb.presentation.common.mapper

import com.alaishat.ahmed.themoviedb.domain.common.model.GenreDomainModel
import com.alaishat.ahmed.themoviedb.domain.common.model.GenresDomainModel
import com.alaishat.ahmed.themoviedb.presentation.common.model.ConnectionStateUiModel
import com.alaishat.ahmed.themoviedb.presentation.common.model.Genre
import com.alaishat.ahmed.themoviedb.presentation.common.model.MainActivityUiState

/**
 * Created by Ahmed Al-Aishat on Oct/04/2023.
 * The Movie DB Project.
 */
fun GenresDomainModel.toViewState(connectionState: ConnectionStateUiModel) = when (this) {
    GenresDomainModel.Loading -> MainActivityUiState.Loading
    GenresDomainModel.NoCache -> MainActivityUiState.NoCache
    is GenresDomainModel.Success -> MainActivityUiState.Success(
        genres = genres.map { it.toViewGenre() },
        connectionState = connectionState
    )
}

private fun GenreDomainModel.toViewGenre() = Genre(id = id, name = name)