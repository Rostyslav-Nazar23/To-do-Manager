package com.rnazarapps.to_domanager.feature_todo.domain.model

data class TodoItem(
    val title: String,
    val text: String,
    val timestamp: Long,
    val completed: Boolean,
    val id: Int
)
