package com.rnazarapps.to_domanager.feature_todo.domain.use_case

import com.rnazarapps.to_domanager.core.utils.TodoUseCaseStrings
import com.rnazarapps.to_domanager.feature_todo.domain.model.TodoItem
import com.rnazarapps.to_domanager.feature_todo.domain.repo.TodoListRepo
import com.rnazarapps.to_domanager.feature_todo.domain.utils.InvalidTodoItemException
import com.rnazarapps.to_domanager.feature_todo.domain.utils.SortingDirection
import com.rnazarapps.to_domanager.feature_todo.domain.utils.TodoItemOrder
import javax.inject.Inject

class TodoUseCases @Inject constructor(
    private val repo: TodoListRepo,
) {
    suspend fun addTodoItem(todoItem: TodoItem) {
        if (todoItem.title.isBlank() || todoItem.text.isBlank()) {
            throw InvalidTodoItemException(TodoUseCaseStrings.EMPTY_TITLE_OR_TEXT) // todo change to string resources
        } else {
            repo.addTodoItem(todoItem)
        }
    }

    suspend fun updateTodoItem(todoItem: TodoItem) {
        if (todoItem.title.isBlank() || todoItem.text.isBlank()) {
            throw InvalidTodoItemException(TodoUseCaseStrings.EMPTY_TITLE_OR_TEXT) // todo change to string resources
        } else {
            repo.updateTodoItem(todoItem)
        }
    }

    suspend fun deleteTodoItem(todoItem: TodoItem) {
        repo.deleteTodoItem(todoItem)
    }

    suspend fun switchCompleted(todoItem: TodoItem) {
        repo.updateTodoItem(todoItem.copy(completed = !todoItem.completed))
    }

    suspend fun getTodoItemById(id: Int): TodoItem? {
        return repo.getTodoItemById(id)
    }

    suspend fun getAllTodoItems(
        todoItemOrder: TodoItemOrder = TodoItemOrder.Time(SortingDirection.Down, true)
    ): Answer<List<TodoItem>> {
        var todoItemsList = repo.getAllTodoItemsFromLocal()

        if (todoItemsList.isEmpty()) {
            todoItemsList = repo.getAllTodoItems()
        }
        // todo move to repo

        val filteredTodoItems = if (todoItemOrder.showCompleted) {
            todoItemsList
        } else {
            todoItemsList.filter { !it.completed }
        }

        return when (todoItemOrder.sortingDirection) {
            is SortingDirection.Down -> {
                when (todoItemOrder) {
                    is TodoItemOrder.Title -> Answer.Success(filteredTodoItems.sortedByDescending { it.title.lowercase() })
                    is TodoItemOrder.Time -> Answer.Success(filteredTodoItems.sortedByDescending { it.timestamp })
                }
            }

            is SortingDirection.Up -> {
                when (todoItemOrder) {
                    is TodoItemOrder.Title -> Answer.Success(filteredTodoItems.sortedBy { it.title.lowercase() })
                    is TodoItemOrder.Time -> Answer.Success(filteredTodoItems.sortedBy { it.timestamp })
                }
            }
        }
    }
}

sealed class Answer<out T> {
    data class Success<T>(val data: T) : Answer<T>()
    data class Fail(val exception: AnswerException) : Answer<Nothing>()
    data object Loading : Answer<Nothing>()
}

sealed interface AnswerException {
    data class InvalidTodoItem(val message: String): AnswerException
}
