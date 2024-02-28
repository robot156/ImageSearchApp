package com.example.imagesearchapp.designsystem.modifier

import android.annotation.SuppressLint
import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.semantics.Role
import kotlinx.coroutines.delay

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.singleClickable(
    interactionSource: MutableInteractionSource,
    indication: Indication?,
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: (() -> Unit)
): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "singleClickable"
        properties["enabled"] = enabled
        properties["onClickLabel"] = onClickLabel
        properties["role"] = role
        properties["onClick"] = onClick
    }
) {
    var enableAgain by remember { mutableStateOf(true) }

    LaunchedEffect(enableAgain) {
        if (enableAgain) return@LaunchedEffect
        delay(timeMillis = DEFAULT_SINGLE_CLICK_DURATION)
        enableAgain = true
    }

    Modifier.clickable(
        enabled = enabled,
        onClickLabel = onClickLabel,
        onClick = {
            if (enableAgain) {
                enableAgain = false
                onClick()
            }
        },
        role = role,
        indication = indication,
        interactionSource = interactionSource
    )
}

fun Modifier.singleClickable(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit
): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "singleClickable"
        properties["enabled"] = enabled
        properties["onClickLabel"] = onClickLabel
        properties["role"] = role
        properties["onClick"] = onClick
    }
) {
    singleClickable(
        enabled = enabled,
        onClickLabel = onClickLabel,
        onClick = onClick,
        role = role,
        indication = rememberRipple(),
        interactionSource = remember { MutableInteractionSource() }
    )
}

private const val DEFAULT_SINGLE_CLICK_DURATION = 500L