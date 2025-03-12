package com.rnazarapps.to_domanager.core.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.stringResource
import com.rnazarapps.to_domanager.R

@Composable
@ReadOnlyComposable
fun getCompleteContentDescription(completed: Boolean): String {
    return if (completed) stringResource(R.string.uncomplete) else stringResource(R.string.complete)
}