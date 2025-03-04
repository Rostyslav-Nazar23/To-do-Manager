package com.rnazarapps.to_domanager.feature_todo.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rnazarapps.to_domanager.feature_todo.data.local.dto.LocalTodoItem

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo_table")
    fun getAllTodoItems(): List<LocalTodoItem>

    @Query("SELECT * FROM todo_table WHERE id = :id")
    suspend fun getLocalTodoItemById(id: Int): LocalTodoItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addListOfLocalTodoItems(todoItems: List<LocalTodoItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addLocalTodoItem(todoItem: LocalTodoItem): Long

    @Delete
    suspend fun deleteLocalTodoItem(todo: LocalTodoItem)
}