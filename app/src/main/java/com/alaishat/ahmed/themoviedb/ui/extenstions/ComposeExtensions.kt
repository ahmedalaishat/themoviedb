package com.alaishat.ahmed.themoviedb.ui.extenstions

import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll

/**
 * Created by Ahmed Al-Aishat on Jun/17/2023.
 * The Movie DB Project.
 */
fun Modifier.verticalNestedScroll(
    outerScrollState: ScrollState,
    dispatcher: NestedScrollDispatcher? = null,
): Modifier = composed {
    val connection = remember { outerScrollState.verticalNestedScrollConnection() }
    nestedScroll(connection, dispatcher)
}

fun ScrollState.verticalNestedScrollConnection() = object : NestedScrollConnection {
    override fun onPreScroll(
        available: Offset,
        source: NestedScrollSource
    ): Offset {
        return if (available.y > 0) Offset.Zero else Offset(
            x = 0f,
            y = -dispatchRawDelta(-available.y)
        )
    }
}