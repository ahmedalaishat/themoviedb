package com.alaishat.ahmed.themoviedb.data.model

import kotlinx.serialization.Serializable

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
@Serializable
data class TestRes(
    val dates: TestDates
)

@Serializable
data class TestDates(
    val maximum: String,
    val minimum: String,
)