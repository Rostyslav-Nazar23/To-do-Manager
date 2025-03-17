package com.rnazarapps.to_domanager.feature_todo.domain.utils

data class TodoItemSorter(
    val sortingType: SortingType,
    val sortingDirection: SortingDirection,
    val showCompleted: Boolean
)
