package com.rnazarapps.to_domanager.feature_todo.data.remote

import com.rnazarapps.to_domanager.feature_todo.data.remote.dto.RemoteTodoItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface TodoApi {
    @GET("todo.json")
    suspend fun getAllTodoItems(): List<RemoteTodoItem>

    @GET("todo.json?orderBy=\"ID\"")
    suspend fun getRemoteTodoItemById(@Query("equalTo") id: Int): Map<String, RemoteTodoItem>

    @PUT
    suspend fun addRemoteTodoItem(@Url url: String, @Body todoItem: RemoteTodoItem): Response<Unit>

    @PUT("todo/{id}.json")
    suspend fun updateRemoteTodoItem(@Path("id") id: Int, @Body remoteTodoItem: RemoteTodoItem): Response<Unit>

    @DELETE("todo/{id}.json")
    suspend fun deleteRemoteTodoItem(@Path("id") id: Int): Response<Unit>
}