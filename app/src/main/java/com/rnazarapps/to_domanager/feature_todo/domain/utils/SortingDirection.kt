package com.rnazarapps.to_domanager.feature_todo.domain.utils

sealed class SortingDirection {
    data object Down: SortingDirection()
    data object Up: SortingDirection()
}