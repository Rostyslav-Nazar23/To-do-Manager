package com.rnazarapps.to_domanager.feature_todo.presentation.todo_list

import com.rnazarapps.to_domanager.feature_todo.domain.model.TodoItem
import com.rnazarapps.to_domanager.feature_todo.domain.use_case.Answer
import com.rnazarapps.to_domanager.feature_todo.domain.utils.SortingDirection
import com.rnazarapps.to_domanager.feature_todo.domain.utils.TodoItemOrder

data class TodoListState(
    val todoItemOrder: TodoItemOrder = TodoItemOrder.Time(sortingDirection = SortingDirection.Down, showCompleted = true),
    val todoList: Answer<List<TodoItem>> = Answer.Loading
)
