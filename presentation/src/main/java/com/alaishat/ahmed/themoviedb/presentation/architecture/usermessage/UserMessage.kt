package com.alaishat.ahmed.themoviedb.presentation.architecture.usermessage

import android.content.Context
import androidx.annotation.StringRes
import java.util.UUID

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
sealed interface UserMessage {
    val id: Long

    fun asString(context: Context): String

    data class StringMessage(
        val message: String,
        override val id: Long = UUID.randomUUID().mostSignificantBits
    ) : UserMessage {
        override fun asString(context: Context): String = message
    }

    data class ResourceMessage(
        @StringRes val resId: Int,
        override val id: Long = UUID.randomUUID().mostSignificantBits
    ) : UserMessage {
        override fun asString(context: Context): String = context.getString(resId)
    }
}