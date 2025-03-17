package com.rnazarapps.to_domanager.feature_todo.domain.repo

import com.rnazarapps.to_domanager.feature_todo.domain.model.TodoItem

interface TodoListRepo {
    suspend fun getAllTodoItems(): List<TodoItem>
    suspend fun getTodoItemById(id: Int): TodoItem?
    suspend fun addTodoItem(todoItem: TodoItem)
    suspend fun updateTodoItem(todoItem: TodoItem)
    suspend fun deleteTodoItem(todoItem: TodoItem)
}
