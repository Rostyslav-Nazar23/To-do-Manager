package com.rnazarapps.to_domanager.feature_todo.presentation.todo_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.rnazarapps.to_domanager.R
import com.rnazarapps.to_domanager.core.presentation.components.getSortingDirectionString
import com.rnazarapps.to_domanager.core.presentation.components.getSortingTypeString
import com.rnazarapps.to_domanager.feature_todo.domain.utils.SortingDirection
import com.rnazarapps.to_domanager.feature_todo.domain.utils.SortingType
import com.rnazarapps.to_domanager.feature_todo.domain.utils.TodoItemSorter

@Composable
fun SortingDrawerOptions(
    todoItemSorter: TodoItemSorter,
    onOrderChange: (TodoItemSorter) -> Unit
) {
    SortingType.entries.forEach {
        SortingTypeDrawerOption(
            sortingType = it,
            todoItemSorter = todoItemSorter,
            onOrderChange = onOrderChange
        )
    }
    SortingDirection.entries.forEach {
        SortingDirectionDrawerOption(
            sortingDirection = it,
            todoItemSorter = todoItemSorter,
            onOrderChange = onOrderChange
        )
    }
    ShowCompletedDrawerOption(
        todoItemSorter = todoItemSorter,
        onOrderChange = onOrderChange
    )
}

@Composable
fun SortingTypeDrawerOption(
    sortingType: SortingType,
    todoItemSorter: TodoItemSorter,
    onOrderChange: (TodoItemSorter) -> Unit
) {
    NavigationDrawerItem(
        label = {
            SortingRow(
                text = getSortingTypeString(sortingType),
                isChecked = todoItemSorter.sortingType == sortingType
            )
        },
        selected = false,
        onClick = { onOrderChange(todoItemSorter.copy(sortingType = sortingType)) },
    )
}

@Composable
fun SortingDirectionDrawerOption(
    sortingDirection: SortingDirection,
    todoItemSorter: TodoItemSorter,
    onOrderChange: (TodoItemSorter) -> Unit
) {
    NavigationDrawerItem(
        label = {
            SortingRow(
                text = getSortingDirectionString(sortingDirection),
                isChecked = todoItemSorter.sortingDirection == sortingDirection
            )
        },
        selected = false,
        onClick = { onOrderChange(todoItemSorter.copy(sortingDirection = sortingDirection)) },
    )
}

@Composable
fun ShowCompletedDrawerOption(
    todoItemSorter: TodoItemSorter,
    onOrderChange: (TodoItemSorter) -> Unit
) {
    NavigationDrawerItem(
        label = {
            SortingRow(
                text = stringResource(R.string.show_completed_sorting_str),
                isChecked = todoItemSorter.showCompleted
            )
        },
        selected = false,
        onClick = { onOrderChange(todoItemSorter.copy(showCompleted = !todoItemSorter.showCompleted)) },
    )
}

@Composable
fun SortingRow(
    modifier: Modifier = Modifier,
    text: String,
    isChecked: Boolean
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = text,
            fontSize = 24.sp,
            lineHeight = 30.sp
        )
        if (isChecked) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = stringResource(R.string.sort_selcted_content_description),
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}
