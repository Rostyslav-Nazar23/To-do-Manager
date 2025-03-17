package com.rnazarapps.to_domanager.feature_todo.presentation.todo_list.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rnazarapps.to_domanager.R
import com.rnazarapps.to_domanager.feature_todo.domain.model.TodoItem
import com.rnazarapps.to_domanager.feature_todo.domain.use_case.Answer
import com.rnazarapps.to_domanager.feature_todo.presentation.todo_list.TodoListEvent
import com.rnazarapps.to_domanager.feature_todo.presentation.todo_list.TodoListViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    navController: NavController,
    viewModel: TodoListViewModel = hiltViewModel<TodoListViewModel>()
) {
    val deletedText = stringResource(R.string.delete_message)
    val undoText = stringResource(R.string.undo)

    val state = viewModel.state.collectAsState().value
    val snackbarHostState = remember { SnackbarHostState() }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.getTodoItems()
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = stringResource(R.string.sort_menu_title),
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(16.dp),
                    fontSize = 34.sp,
                    lineHeight = 38.sp
                )
                HorizontalDivider()
                SortingDrawerOptions(
                    todoItemSorter = state.todoItemSorter
                ) { order ->
                    viewModel.onEvent(TodoListEvent.Sort(order))
                }
            }
        }
    ) {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { TODO() },
                    shape = CircleShape,
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(R.string.add_todoitem_content_description),
                        tint = contentColorFor(MaterialTheme.colorScheme.primary)
                    )
                }
            },
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.app_name),
                            color = contentColorFor(MaterialTheme.colorScheme.primaryContainer),
                            maxLines = 1,
                            style = MaterialTheme.typography.headlineLarge
                        )
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        navigationIconContentColor = contentColorFor(MaterialTheme.colorScheme.primaryContainer),
                        scrolledContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = contentColorFor(MaterialTheme.colorScheme.primaryContainer),
                        actionIconContentColor = contentColorFor(MaterialTheme.colorScheme.primaryContainer)
                    ),
                    navigationIcon = {},
                    actions = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = stringResource(R.string.sort_menu_content_description),
                            )
                        }
                    },
                    scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
                )
            },
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            }
        ) { paddingValues ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 16.dp)
                            .padding(horizontal = 12.dp)
                    ) {
                        val res = state.todoList
                        if (res is Answer.Success<List<TodoItem>>) {
                            items(res.data) { todoItem ->
                                TodoItemCard(
                                    modifier = Modifier.padding(4.dp),
                                    todoItem = todoItem,
                                    onDeleteClick = {
                                        viewModel.onEvent(event = TodoListEvent.Delete(todoItem))
                                        scope.launch {
                                            val undo = snackbarHostState.showSnackbar(
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
                                    onCardClick = { TODO() }
                                )
                            }
                        }
                    }
                }
                if (state.todoList == Answer.Loading) {
                    val loadingContentDescription =
                        stringResource(R.string.loading_content_description)
                    CircularProgressIndicator(
                        modifier = Modifier.semantics {
                            this.contentDescription = loadingContentDescription
                        }
                    )
                }
                if (state.todoList is Answer.Fail) {
                    TODO()
                }
            }
        }
    }
}


