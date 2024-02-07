package com.alaishat.ahmed.themoviedb.domain.common.model

/**
 * Created by Ahmed Al-Aishat on Oct/03/2023.
 * The Movie DB Project.
 */
sealed interface GenresDomainModel {

    data class Success(
        val genres: List<GenreDomainModel>,
    ) : GenresDomainModel

    data object NoCache : GenresDomainModel

    data object Loading : GenresDomainModel
}