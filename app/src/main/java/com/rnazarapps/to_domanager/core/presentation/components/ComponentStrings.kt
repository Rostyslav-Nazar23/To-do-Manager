package com.rnazarapps.to_domanager.core.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.stringResource
import com.rnazarapps.to_domanager.R
import com.rnazarapps.to_domanager.feature_todo.domain.utils.SortingDirection
import com.rnazarapps.to_domanager.feature_todo.domain.utils.SortingType

@Composable
@ReadOnlyComposable
fun getCompleteContentDescription(completed: Boolean): String {
    return if (completed) stringResource(R.string.uncomplete) else stringResource(R.string.complete)
}

@Composable
@ReadOnlyComposable
fun getSortingTypeString(sortingType: SortingType): String {
    return when(sortingType) {
        SortingType.Title -> stringResource(R.string.title_sorting_str)
        SortingType.Time -> stringResource(R.string.time_sorting_str)
    }
}

@Composable
@ReadOnlyComposable
fun getSortingDirectionString(sortingDirection: SortingDirection): String {
    return when(sortingDirection) {
        SortingDirection.Down -> stringResource(R.string.down_sorting_str)
        SortingDirection.Up -> stringResource(R.string.up_sorting_str)
    }
}
