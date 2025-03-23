package com.rnazarapps.to_domanager.feature_todo.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RemoteTodoItem(
    @SerializedName("Title")
    val title: String,
    @SerializedName("Text")
    val text: String,
    @SerializedName("Timestamp")
    val timestamp: Long,
    @SerializedName("Completed")
    val completed: Boolean,
    @SerializedName("ID")
    val id: Int?
)
