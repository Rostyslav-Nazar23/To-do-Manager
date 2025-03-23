package com.rnazarapps.to_domanager.core.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import com.rnazarapps.to_domanager.ui.theme.completedColor

@Composable
@ReadOnlyComposable
fun getCompleteButtonColor(completed: Boolean): Color {
    return when(completed) {
        true -> completedColor
        false -> MaterialTheme.colorScheme.onSecondary
    }
}

@Composable
@ReadOnlyComposable
fun getCardContainerColor(completed: Boolean): Color {
    return when(completed) {
        true -> MaterialTheme.colorScheme.surfaceContainerHighest
        false -> MaterialTheme.colorScheme.surfaceContainer
    }
}


@Composable
@ReadOnlyComposable
fun getCardTitleContainerColor(completed: Boolean): Color {
    return when(completed) {
        true -> MaterialTheme.colorScheme.secondaryContainer
        false -> MaterialTheme.colorScheme.primaryContainer
    }
}
