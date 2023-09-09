package com.alaishat.ahmed.themoviedb.ui.component

import android.view.animation.AnticipateOvershootInterpolator
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.alaishat.ahmed.themoviedb.R
import com.alaishat.ahmed.themoviedb.ui.extenstions.lighter
import com.alaishat.ahmed.themoviedb.ui.extenstions.toEasing
import com.alaishat.ahmed.themoviedb.ui.theme.AppGreen
import com.alaishat.ahmed.themoviedb.ui.theme.AppRed
import com.alaishat.ahmed.themoviedb.ui.theme.Dimensions

/**
 * Created by Ahmed Al-Aishat on Sep/09/2023.
 * The Movie DB Project.
 */
@Composable
fun ConfirmationDialog(
    dialogState: DialogState,
    message: String,
    onConfirm: () -> Unit
) {
    if (!dialogState.isShown.value) return

    Dialog(
        onDismissRequest = {
            dialogState.dismiss()
        }) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(size = 16.dp)
        ) {
            Column {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 16.dp),
                    text = message,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.outlineVariant)
                        .padding(top = Dimensions.BorderSize),
                    horizontalArrangement = Arrangement.spacedBy(Dimensions.BorderSize)
                ) {
                    Box(
                        modifier = Modifier
                            .height(48.dp)
                            .weight(1f)
                            .background(MaterialTheme.colorScheme.background)
                            .clickable(onClick = dialogState::dismiss),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = stringResource(id = R.string.no),
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }
                    Box(
                        modifier = Modifier
                            .height(48.dp)
                            .weight(1f)
                            .background(MaterialTheme.colorScheme.background)
                            .clickable {
                                onConfirm()
                                dialogState.dismiss()
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = stringResource(id = R.string.yes),
                            color = AppRed,
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }
                }
            }
        }
    }
}

data class DialogState(
    val isShown: MutableState<Boolean> = mutableStateOf(false)
) {
    fun show() {
        isShown.value = true
    }

    fun dismiss() {
        isShown.value = false
    }
}

@Composable
fun rememberDialogState(initialValue: Boolean = false): DialogState {
    return remember { DialogState(mutableStateOf(initialValue)) }
}


@Composable
fun SuccessDialog(
    dialogState: DialogState,
    message: String,
    onPositiveClick: () -> Unit
) {
    if (!dialogState.isShown.value) return

    Dialog(
        onDismissRequest = {
            dialogState.dismiss()
        },
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                var visible by remember { mutableStateOf(false) }

                val scale by animateFloatAsState(
                    if (visible) 1f else 0.5f,
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = AnticipateOvershootInterpolator().toEasing()
                    ),
                    label = ""
                )

                LaunchedEffect(Unit) {
                    visible = true
                }

                Row(
                    modifier = Modifier
                        .padding(top = Dimensions.MarginMd)
                        .size(Dimensions.IconXLg)
                        .clip(RoundedCornerShape(Dimensions.IconXLg))
                        .background(AppGreen.lighter(.8f)),
                ) {
                    AnimatedVisibility(
                        modifier = Modifier.size(Dimensions.IconXLg), visible = visible, enter = fadeIn(
                            animationSpec = TweenSpec(500, 0, AnticipateOvershootInterpolator().toEasing())
                        )
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(Dimensions.IconXLg)
                                .scale(scale),
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = AppGreen
                        )
                    }
                }
                Text(
                    modifier = Modifier
                        .padding(Dimensions.MarginMd)
                        .fillMaxWidth(),
                    text = message,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.background,
                )
                Divider(color = MaterialTheme.colorScheme.outlineVariant)
                Box(
                    modifier = Modifier
                        .height(48.dp)
                        .fillMaxWidth()
                        .clickable {
                            onPositiveClick()
                            dialogState.dismiss()
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = stringResource(id = R.string.ok),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.labelLarge,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ConfirmationDialogPreview() {
    MaterialTheme {
        Surface {
            ConfirmationDialog(
                dialogState = rememberDialogState(true),
                message = stringResource(id = R.string.confirmation_message),
                onConfirm = { },
            )
        }
    }
}

@Preview
@Composable
private fun SuccessDialogPreview() {
    MaterialTheme {
        Surface {
            SuccessDialog(
                dialogState = rememberDialogState(true),
                message = stringResource(R.string.rating_success_message),
                onPositiveClick = { },
            )
        }
    }
}
