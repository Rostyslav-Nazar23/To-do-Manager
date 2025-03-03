package com.rnazarapps.to_domanager.feature_todo.data.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class LocalTodoItem(
    val title: String,
    val text: String,
    val timestamp: Long,
    val completed: Boolean,
    @PrimaryKey(autoGenerate = true)
    val id: Int
)
