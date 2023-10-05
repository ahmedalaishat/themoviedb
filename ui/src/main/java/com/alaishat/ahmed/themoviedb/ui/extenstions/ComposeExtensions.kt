package com.alaishat.ahmed.themoviedb.ui.extenstions

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.Density
import androidx.core.graphics.ColorUtils
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

/**
 * Created by Ahmed Al-Aishat on Jun/17/2023.
 * The Movie DB Project.
 */
fun Modifier.silentClickable(
    silent: Boolean = true,
    onClick: () -> Unit
) = if (!silent) clickable { onClick() }
else clickable(
    interactionSource = MutableInteractionSource(),
    indication = null,
    onClick = onClick
)

fun Color.darker(ratio: Float = .9f) = Color(ColorUtils.blendARGB(this.toArgb(), Color.Black.toArgb(), ratio))

fun Color.lighter(ratio: Float = .9f) = Color(ColorUtils.blendARGB(this.toArgb(), Color.White.toArgb(), ratio))

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

val FooterArrangement = object : Arrangement.Vertical {
    override fun Density.arrange(
        totalSize: Int,
        sizes: IntArray,
        outPositions: IntArray
    ) {
        var currentOffset = 0
        sizes.forEachIndexed { index, size ->
            if (index == sizes.lastIndex) {
                outPositions[index] = totalSize - size
            } else {
                outPositions[index] = currentOffset
                currentOffset += size
            }
        }
    }
}

fun LazyGridScope.pagingInitialLoader(
    loadState: CombinedLoadStates,
    content: LazyGridScope.() -> Unit
) {
    // First Loading
    if (loadState.refresh == LoadState.Loading) content()
}

fun LazyGridScope.pagingLoader(
    loadState: CombinedLoadStates,
    content: LazyGridScope.() -> Unit
) {
    // Pagination Loading
    if (loadState.append == LoadState.Loading) content()
}

fun LazyListScope.pagingInitialLoader(
    loadState: CombinedLoadStates,
    content: LazyListScope.() -> Unit
) {
    // First Loading
    if (loadState.refresh == LoadState.Loading) content()
}

fun LazyListScope.pagingLoader(
    loadState: CombinedLoadStates,
    content: LazyListScope.() -> Unit
) {
    // Pagination Loading
    if (loadState.append == LoadState.Loading) content()
}

@Composable
fun PagingInitialLoader(
    loadState: CombinedLoadStates,
    content: @Composable () -> Unit
) {
    // First Loading
    if (loadState.refresh == LoadState.Loading) content()
}

@Composable
fun PagingErrorBox(
    pagingItems: LazyPagingItems<*>,
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    content: @Composable BoxScope.(errorMessage: String?) -> Unit
) {
    // Pagination Error
    if (pagingItems.loadState.refresh is LoadState.Error)
        Box(
            modifier = modifier,
            contentAlignment = contentAlignment,
            content = { content((pagingItems.loadState.refresh as LoadState.Error).error.message) }
        )
}

@Composable
fun PagingEmptyBox(
    pagingItems: LazyPagingItems<*>,
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    content: @Composable BoxScope.() -> Unit
) {
    // Pagination Reached End && Not Loading
    if (pagingItems.loadState.append.endOfPaginationReached && pagingItems.itemCount == 0)
        Box(
            modifier = modifier,
            contentAlignment = contentAlignment,
            content = content
        )
}

fun LazyGridScope.maxLineBox(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    item(span = { GridItemSpan(maxLineSpan) }) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center,
            content = content
        )
    }
}

@Composable
fun <T : Any> LazyPagingItems<T>.rememberLazyListState(): LazyListState {
    // After recreation, LazyPagingItems first return 0 items, then the cached items.
    // This behavior/issue is resetting the LazyListState scroll position.
    // Below is a workaround. More info: https://issuetracker.google.com/issues/177245496.
    return when (itemCount) {
        // Return a different LazyListState instance.
        0 -> remember(this) { LazyListState(0, 0) }
        // Return rememberLazyListState (normal case).
        else -> androidx.compose.foundation.lazy.rememberLazyListState()
    }
}