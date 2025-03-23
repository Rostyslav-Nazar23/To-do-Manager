package com.rnazarapps.to_domanager.feature_todo.presentation.todo_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rnazarapps.to_domanager.feature_todo.data.di.IODispatcher
import com.rnazarapps.to_domanager.feature_todo.domain.model.TodoItem
import com.rnazarapps.to_domanager.feature_todo.domain.use_case.TodoUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val todoUseCases: TodoUseCases,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _state = MutableStateFlow(TodoListState())
    val state: StateFlow<TodoListState> = _state.asStateFlow()

    private var getTodoItemsJob: Job? = null
    private var undoTodoItem: TodoItem? = null

    fun getTodoItems() {
        getTodoItemsJob?.cancel()
        getTodoItemsJob = viewModelScope.launch(dispatcher) {
            _state.value = _state.value.copy(
                todoList = todoUseCases.getAllTodoItems(_state.value.todoItemSorter)
            )
        }
    }

    fun onEvent(event: TodoListEvent) {
        when (event) {
            is TodoListEvent.Delete -> {
                viewModelScope.launch(dispatcher) {
                    todoUseCases.deleteTodoItem(event.todoItem)
                    getTodoItems()
                    undoTodoItem = event.todoItem
                }
            }

            is TodoListEvent.Sort -> {
                viewModelScope.launch(dispatcher) {
                    val stateOrderMatchesEventOrder =
                        _state.value.todoItemSorter == event.todoItemSorter &&
                                _state.value.todoItemSorter.showCompleted == event.todoItemSorter.showCompleted &&
                                _state.value.todoItemSorter.sortingDirection == event.todoItemSorter.sortingDirection
                    if (stateOrderMatchesEventOrder) {
                        return@launch
                    }
                    _state.value = _state.value.copy(
                        todoItemSorter = event.todoItemSorter
                    )
                    getTodoItems()
                }
            }

            is TodoListEvent.SwitchCompleted -> {
                viewModelScope.launch(dispatcher) {
                    todoUseCases.switchCompleted(event.todoItem)
                    getTodoItems()
                }
            }

            is TodoListEvent.UndoDelete -> {
                viewModelScope.launch(dispatcher) {
                    todoUseCases.addTodoItem(undoTodoItem ?: return@launch)
                    undoTodoItem = null
                    getTodoItems()
                }
            }
        }
    }
}
