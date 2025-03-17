package com.rnazarapps.to_domanager.feature_todo.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rnazarapps.to_domanager.core.presentation.utils.Screen
import com.rnazarapps.to_domanager.feature_todo.presentation.todo_list.TodoListViewModel
import com.rnazarapps.to_domanager.feature_todo.presentation.todo_list.components.TodoListScreen
import com.rnazarapps.to_domanager.ui.theme.TodoManagerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoManagerTheme {
                Surface(color = MaterialTheme.colorScheme.surfaceContainerLowest) {
                    val navController = rememberNavController()
                    val todoListViewModel: TodoListViewModel = hiltViewModel()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.TodoItemListScreen.route
                    ) {
                        composable(route = Screen.TodoItemListScreen.route) {
                            TodoListScreen(
                                navController = navController,
                                viewModel = todoListViewModel
                            )
                        }
                        composable(route = Screen.TodoItemEditScreen.route) {
                            TODO()
                        }
                    }
                }
            }
        }
    }
}
