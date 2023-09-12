package com.alaishat.ahmed.themoviedb.architecture.usermessage

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.util.UUID

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
sealed interface UserMessage {
    val id: Long

    @Composable
    fun asString(): String

    data class StringMessage(
        val message: String,
        override val id: Long = UUID.randomUUID().mostSignificantBits
    ) : UserMessage {
        @Composable
        override fun asString(): String = message
    }

    data class ResourceMessage(
        @StringRes val resId: Int,
        override val id: Long = UUID.randomUUID().mostSignificantBits
    ) : UserMessage {
        @Composable
        override fun asString(): String = LocalContext.current.getString(resId)
    }
}