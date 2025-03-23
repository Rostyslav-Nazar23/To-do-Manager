package com.rnazarapps.to_domanager.feature_todo.presentation.todo_list

import com.rnazarapps.to_domanager.feature_todo.data.repo.RepoAnswer
import com.rnazarapps.to_domanager.feature_todo.domain.model.TodoItem
import com.rnazarapps.to_domanager.feature_todo.domain.utils.SortingDirection
import com.rnazarapps.to_domanager.feature_todo.domain.utils.SortingType
import com.rnazarapps.to_domanager.feature_todo.domain.utils.TodoItemSorter

data class TodoListState(
    val todoItemSorter: TodoItemSorter = TodoItemSorter(
        sortingType = SortingType.Time,
        sortingDirection = SortingDirection.Down,
        showCompleted = true
    ),
    val todoList: RepoAnswer<List<TodoItem>> = RepoAnswer.Loading
)
