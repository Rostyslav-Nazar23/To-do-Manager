package com.rnazarapps.to_domanager.feature_todo.presentation.todo_list

import com.rnazarapps.to_domanager.feature_todo.domain.model.TodoItem
import com.rnazarapps.to_domanager.feature_todo.domain.utils.TodoItemOrder

sealed class TodoListEvent {
    data class Sort(val todoItemOrder: TodoItemOrder): TodoListEvent()
    data class Delete(val todoItem: TodoItem): TodoListEvent()
    data class SwitchCompleted(val todoItem: TodoItem): TodoListEvent()
    data object UndoDelete: TodoListEvent()
}