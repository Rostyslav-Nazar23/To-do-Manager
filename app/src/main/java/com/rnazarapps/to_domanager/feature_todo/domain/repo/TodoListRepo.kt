package com.rnazarapps.to_domanager.feature_todo.domain.repo

import com.rnazarapps.to_domanager.feature_todo.data.repo.RepoAnswer
import com.rnazarapps.to_domanager.feature_todo.domain.model.TodoItem

interface TodoListRepo {
    suspend fun getAllTodoItems(): RepoAnswer<List<TodoItem>>
    suspend fun getTodoItemById(id: Int): RepoAnswer<TodoItem>
    suspend fun addTodoItem(todoItem: TodoItem)
    suspend fun updateTodoItem(todoItem: TodoItem)
    suspend fun deleteTodoItem(todoItem: TodoItem)
}
