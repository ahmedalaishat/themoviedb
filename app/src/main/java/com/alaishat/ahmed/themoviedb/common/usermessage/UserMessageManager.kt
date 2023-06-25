package com.alaishat.ahmed.themoviedb.common.usermessage

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Ahmed Al-Aishat on Jun/25/2023.
 * The Movie DB Project.
 */
@Singleton
object UserMessageManager {

    private val _messages: MutableStateFlow<List<UserMessage>> = MutableStateFlow(emptyList())
    val messages: StateFlow<List<UserMessage>> get() = _messages.asStateFlow()

    fun showMessage(message: UserMessage) {
        _messages.update { currentMessages ->
            currentMessages + message
        }
    }

    fun setMessageShown(messageId: Long) {
        _messages.update { currentMessages ->
            currentMessages.filterNot { it.id == messageId }
        }
    }
}