package com.rnazarapps.to_domanager.core.presentation.utils

sealed class Screen(val route: String) {
    data object TodoItemListScreen: Screen(route = "todoItemList_screen")
    data object TodoItemEditScreen: Screen(route = "todoItemEdit_screen")
}