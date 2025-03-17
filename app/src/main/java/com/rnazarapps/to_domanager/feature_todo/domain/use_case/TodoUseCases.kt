package com.rnazarapps.to_domanager.feature_todo.domain.use_case

import com.rnazarapps.to_domanager.feature_todo.domain.model.TodoItem
import com.rnazarapps.to_domanager.feature_todo.domain.repo.TodoListRepo
import com.rnazarapps.to_domanager.feature_todo.domain.utils.InvalidTodoItemException
import com.rnazarapps.to_domanager.feature_todo.domain.utils.SortingDirection
import com.rnazarapps.to_domanager.feature_todo.domain.utils.SortingType
import com.rnazarapps.to_domanager.feature_todo.domain.utils.TodoItemSorter
import javax.inject.Inject

class TodoUseCases @Inject constructor(
    private val repo: TodoListRepo,
) {
    suspend fun addTodoItem(todoItem: TodoItem) {
        if (todoItem.title.isBlank() || todoItem.text.isBlank()) {
            throw InvalidTodoItemException("Both title an text should be filled.")
        } else {
            repo.addTodoItem(todoItem)
        }
    }

    suspend fun updateTodoItem(todoItem: TodoItem) {
        if (todoItem.title.isBlank() || todoItem.text.isBlank()) {
            throw InvalidTodoItemException("Both title an text should be filled.")
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
        todoItemSorter: TodoItemSorter = TodoItemSorter(
            sortingType = SortingType.Time,
            sortingDirection = SortingDirection.Down,
            showCompleted = true
        )
    ): Answer<List<TodoItem>> {
        val todoItemsList = repo.getAllTodoItems()

        return Answer.Success(
            sortTodoItems(
                todoItemsList = todoItemsList,
                todoItemSorter = todoItemSorter
            )
        )
    }

    fun sortTodoItems(
        todoItemsList: List<TodoItem>,
        todoItemSorter: TodoItemSorter = TodoItemSorter(
            sortingType = SortingType.Time,
            sortingDirection = SortingDirection.Down,
            showCompleted = true
        )
    ): List<TodoItem> {
        val filteredTodoItems = if (todoItemSorter.showCompleted) {
            todoItemsList
        } else {
            todoItemsList.filter { !it.completed }
        }


        return when (todoItemSorter.sortingDirection) {
            SortingDirection.Down -> {
                when (todoItemSorter.sortingType) {
                    SortingType.Title -> filteredTodoItems.sortedByDescending { it.title.lowercase() }
                    SortingType.Time -> filteredTodoItems.sortedByDescending { it.timestamp }
                }
            }

            SortingDirection.Up -> {
                when (todoItemSorter.sortingType) {
                    SortingType.Title -> filteredTodoItems.sortedBy { it.title.lowercase() }
                    SortingType.Time -> filteredTodoItems.sortedBy { it.timestamp }
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
    data class InvalidTodoItem(val message: String) : AnswerException
}
