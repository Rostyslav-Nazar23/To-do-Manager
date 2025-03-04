package com.rnazarapps.to_domanager.feature_todo.data.repo

import android.util.Log
import com.rnazarapps.to_domanager.feature_todo.data.di.IODispatcher
import com.rnazarapps.to_domanager.feature_todo.data.local.TodoDao
import com.rnazarapps.to_domanager.feature_todo.data.mapper.toLocalItemListFromRemote
import com.rnazarapps.to_domanager.feature_todo.data.mapper.toLocalTodoItem
import com.rnazarapps.to_domanager.feature_todo.data.mapper.toRemoteTodoItem
import com.rnazarapps.to_domanager.feature_todo.data.mapper.toTodoItem
import com.rnazarapps.to_domanager.feature_todo.data.mapper.toTodoItemListFromLocal
import com.rnazarapps.to_domanager.feature_todo.data.remote.TodoApi
import com.rnazarapps.to_domanager.feature_todo.domain.model.TodoItem
import com.rnazarapps.to_domanager.feature_todo.domain.repo.TodoListRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException

class TodoListRepoImpl(
    private val dao: TodoDao,
    private val api: TodoApi,
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher
) : TodoListRepo {
    override suspend fun getAllTodoItems(): List<TodoItem> {
        getAllTodoItemsFromRemote()
        return dao.getAllTodoItems().toTodoItemListFromLocal()
    }

    override suspend fun getAllTodoItemsFromLocal(): List<TodoItem> {
        return dao.getAllTodoItems().toTodoItemListFromLocal()
    }

    override suspend fun getAllTodoItemsFromRemote() {
        return withContext(coroutineDispatcher) {
            try {
                refreshLocal()
            } catch (e: Exception) {
                when (e) {
                    is UnknownHostException, is ConnectException, is HttpException -> {
                        Log.e("HTTP", "Error: No data from remote")
                        if (isLocalEmpty()) {
                            Log.e("Local", "Error: No data from local")
                            throw Exception("Error: No Internet, Local has no data")
                        }
                    }

                    else -> throw Exception()
                }
            }
        }
    }

    private suspend fun refreshLocal() {
        val remoteTodoItems = api.getAllTodoItems()
        dao.addListOfLocalTodoItems(remoteTodoItems.toLocalItemListFromRemote())
    }

    private fun isLocalEmpty(): Boolean {
        var isEmpty = true
        if (dao.getAllTodoItems().isNotEmpty()) isEmpty = false
        return isEmpty
    }

    override suspend fun getTodoItemById(id: Int): TodoItem? {
        return dao.getLocalTodoItemById(id)?.toTodoItem()
    }

    override suspend fun addTodoItem(todoItem: TodoItem) {
        val id = dao.addLocalTodoItem(todoItem.toLocalTodoItem()).toInt()
        val url = "todo/$id.json"
        api.addRemoteTodoItem(url, todoItem.toRemoteTodoItem().copy(id = id))
    }

    override suspend fun updateTodoItem(todoItem: TodoItem) {
        dao.addLocalTodoItem(todoItem.toLocalTodoItem())
        api.updateRemoteTodoItem(id = todoItem.id, remoteTodoItem = todoItem.toRemoteTodoItem())
    }

    override suspend fun deleteTodoItem(todoItem: TodoItem) {
        try {
            val response = api.deleteRemoteTodoItem(todoItem.id)
            if (response.isSuccessful) {
                Log.i("API_DELETE", "Response Successful")
            } else {
                Log.i("API_DELETE", "Response Unsuccessful: ${response.message()}")
            }
        } catch (e: Exception) {
            when (e) {
                is UnknownHostException, is ConnectException, is HttpException -> {
                    Log.e("HTTP", "Error: Could not delete item")
                }

                else -> throw e
            }
        }
    }

}