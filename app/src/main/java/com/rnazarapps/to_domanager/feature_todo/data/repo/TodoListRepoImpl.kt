package com.rnazarapps.to_domanager.feature_todo.data.repo

import android.database.sqlite.SQLiteDatabaseLockedException
import android.database.sqlite.SQLiteDiskIOException
import android.util.Log
import com.rnazarapps.to_domanager.feature_todo.data.di.IODispatcher
import com.rnazarapps.to_domanager.feature_todo.data.local.TodoDao
import com.rnazarapps.to_domanager.feature_todo.data.local.dto.LocalTodoItem
import com.rnazarapps.to_domanager.feature_todo.data.mapper.toLocalTodoItem
import com.rnazarapps.to_domanager.feature_todo.data.mapper.toRemoteTodoItem
import com.rnazarapps.to_domanager.feature_todo.data.mapper.toTodoItem
import com.rnazarapps.to_domanager.feature_todo.data.mapper.toTodoItemListFromLocal
import com.rnazarapps.to_domanager.feature_todo.data.remote.TodoApi
import com.rnazarapps.to_domanager.feature_todo.data.remote.dto.RemoteTodoItem
import com.rnazarapps.to_domanager.feature_todo.domain.model.TodoItem
import com.rnazarapps.to_domanager.feature_todo.domain.repo.TodoListRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class TodoListRepoImpl(
    private val dao: TodoDao,
    private val api: TodoApi,
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher
) : TodoListRepo {
    override suspend fun getAllTodoItems(): RepoAnswer<List<TodoItem>> =
        withContext(coroutineDispatcher) {
            val apiRes: List<RemoteTodoItem?>?
            val localRes: List<LocalTodoItem>?
            try {
                localRes = dao.getAllTodoItems()

                try {
                    apiRes = api.getAllTodoItems()
                    apiRes.forEach { remoteItem ->
                        if (remoteItem != null) {
                            if (!localRes.contains(remoteItem.toLocalTodoItem())) {
                                dao.addLocalTodoItem(remoteItem.toLocalTodoItem())
                            }
                        }

                    }

                    //todo

                    return@withContext RepoAnswer.Success(
                        dao.getAllTodoItems().toTodoItemListFromLocal()
                    )

                } catch (apiException: Exception) {
                    apiException.printStackTrace()
                    Log.d("TAG", "${apiException.message}")

                    return@withContext when (apiException) {

                        is ConnectException, is SocketTimeoutException, is UnknownHostException -> {
                            RepoAnswer.PartialSuccess(
                                data = dao.getAllTodoItems().toTodoItemListFromLocal(),
                                exception = RepoException.InternetError("Couldn't get data from remote. No Internet connection.")
                            )
                        }

                        is HttpException -> {
                            RepoAnswer.PartialSuccess(
                                data = dao.getAllTodoItems().toTodoItemListFromLocal(),
                                exception = RepoException.ConnectionError("Couldn't get data from remote. No connection to server.")
                            )
                        }

                        else -> {
                            RepoAnswer.PartialSuccess(
                                data = dao.getAllTodoItems().toTodoItemListFromLocal(),
                                exception = RepoException.ConnectionError("Couldn't get data from remote. Error: ${apiException.message}")
                            )
                        }
                    }
                }
            } catch (localException: Exception) {
                return@withContext when (localException) {
                    is SQLiteDiskIOException -> {
                        RepoAnswer.Fail(exception = RepoException.DiskIOException("Couldn't read data from disk"))
                    }

                    is SQLiteDatabaseLockedException -> {
                        RepoAnswer.Fail(RepoException.DatabaseLocked(message = "Couldn't read data. Database is used by another process"))
                    }

                    else -> {
                        throw localException
                    }
                }
            }
        }

    override suspend fun getTodoItemById(id: Int): RepoAnswer<TodoItem> =
        withContext(coroutineDispatcher) {
            try {
                val res = dao.getLocalTodoItemById(id)
                return@withContext if (res != null) {
                    RepoAnswer.Success(res.toTodoItem())
                } else {
                    RepoAnswer.Fail(RepoException.NullItemResponse("No item with such id found"))
                }
            } catch (e: Exception) {
                when(e) {
                    is SQLiteDiskIOException -> {
                        RepoAnswer.Fail(exception = RepoException.DiskIOException("Couldn't read data from disk"))
                    }

                    is SQLiteDatabaseLockedException -> {
                        RepoAnswer.Fail(RepoException.DatabaseLocked(message = "Couldn't read data. Database is used by another process"))
                    }
                    else -> throw e
                }
            }
        }

    override suspend fun addTodoItem(todoItem: TodoItem) {
        val id = dao.addLocalTodoItem(todoItem.toLocalTodoItem()).toInt()
        val url = "todo/$id.json"
        api.addRemoteTodoItem(url, todoItem.toRemoteTodoItem().copy(id = id))
    }

    override suspend fun updateTodoItem(todoItem: TodoItem) {
        dao.addLocalTodoItem(todoItem.toLocalTodoItem())
        api.updateRemoteTodoItem(id = todoItem.id!!, remoteTodoItem = todoItem.toRemoteTodoItem())
    }

    override suspend fun deleteTodoItem(todoItem: TodoItem) {
        try {
            dao.deleteLocalTodoItem(todoItem.toLocalTodoItem())
            val response = api.deleteRemoteTodoItem(todoItem.id!!)
            if (response.isSuccessful) {
                Log.i("API_DELETE", "Response Successful ${todoItem.id}")
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

sealed class RepoAnswer<out T> {
    data class Success<T>(val data: T) : RepoAnswer<T>()
    data class PartialSuccess<T>(val data: T, val exception: RepoException) : RepoAnswer<T>()
    data class Fail(val exception: RepoException) : RepoAnswer<Nothing>()
    data object Loading : RepoAnswer<Nothing>()
}

sealed interface RepoException {
    data class NullItemResponse(val message: String) : RepoException
    data class DiskIOException(val message: String) : RepoException
    data class DatabaseLocked(val message: String) : RepoException
    data class ConnectionError(val message: String) : RepoException
    data class InternetError(val message: String) : RepoException
}
