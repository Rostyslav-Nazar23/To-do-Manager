package com.rnazarapps.to_domanager.feature_todo.domain.utils

sealed class TodoItemOrder(
    val sortingDirection: SortingDirection,
    val showCompleted: Boolean
) {
    class Title(sortingDirection: SortingDirection, showCompleted: Boolean): TodoItemOrder(sortingDirection, showCompleted)
    class Time(sortingDirection: SortingDirection, showCompleted: Boolean): TodoItemOrder(sortingDirection, showCompleted)

    fun copy(sortingDirection: SortingDirection, showCompleted: Boolean): TodoItemOrder {
        return when(this) {
            is Title -> Title(sortingDirection, showCompleted)
            is Time -> Time(sortingDirection, showCompleted)
        }
    }
}