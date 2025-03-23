package com.rnazarapps.to_domanager.feature_todo.presentation.todo_list.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rnazarapps.to_domanager.R
import com.rnazarapps.to_domanager.feature_todo.domain.model.TodoItem
import com.rnazarapps.to_domanager.feature_todo.presentation.todo_list.TodoListEvent
import com.rnazarapps.to_domanager.feature_todo.presentation.todo_list.TodoListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun TodoItemCardsColumn(
    todoItems: List<TodoItem>,
    viewModel: TodoListViewModel,
    scope: CoroutineScope,
    snackBarHostState: SnackbarHostState
) {
    val deletedText = stringResource(R.string.delete_message)
    val undoText = stringResource(R.string.undo)
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
            .padding(horizontal = 12.dp)
    ) {
        items(todoItems) { todoItem ->
            TodoItemCard(
                modifier = Modifier.padding(4.dp),
                todoItem = todoItem,
                onDeleteClick = {
                    viewModel.onEvent(event = TodoListEvent.Delete(todoItem))
                    scope.launch {

                        if (snackBarHostState.currentSnackbarData != null) {
                            snackBarHostState.currentSnackbarData!!.dismiss()
                        }
                        val undo = snackBarHostState.showSnackbar(
                            withDismissAction = true,
                            message = deletedText,
                            actionLabel = undoText
                        )
                        if (undo == SnackbarResult.ActionPerformed) {
                            viewModel.onEvent(TodoListEvent.UndoDelete)
                        }
                    }
                },
                onCompleteClick = {
                    viewModel.onEvent(
                        event = TodoListEvent.SwitchCompleted(
                            todoItem
                        )
                    )
                },
                onCardClick = {
                    // todo item edit
                }
            )
        }
    }
}