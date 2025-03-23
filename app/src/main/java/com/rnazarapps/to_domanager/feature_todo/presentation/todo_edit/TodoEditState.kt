package com.rnazarapps.to_domanager.feature_todo.presentation.todo_edit

import com.rnazarapps.to_domanager.feature_todo.domain.model.TodoItem

data class TodoEditState(
    val isTitleHintVisible: Boolean = true,
    val isDescriptionVisible: Boolean = true,
    val todoItem: TodoItem = TodoItem(
        title = "",
        text = "",
        timestamp = 0,
        completed = false,
        id = null,
    ),
    val isLoading: Boolean = true,
    val editResult: EditResult? = null
)

sealed class EditResult {
    data object Success : EditResult()
    data class Fail(val exception: EditException) : EditResult()
}

sealed interface EditException {
    data class InvalidTodoItem(val message: String) : EditException
    data class DatabaseError(val message: String) : EditException
}
