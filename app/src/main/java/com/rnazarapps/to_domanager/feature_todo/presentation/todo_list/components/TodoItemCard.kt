package com.rnazarapps.to_domanager.feature_todo.presentation.todo_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rnazarapps.to_domanager.R
import com.rnazarapps.to_domanager.core.presentation.components.CompleteButton
import com.rnazarapps.to_domanager.core.presentation.components.DeleteButton
import com.rnazarapps.to_domanager.core.presentation.components.getCardContainerColor
import com.rnazarapps.to_domanager.core.presentation.components.getCardTitleContainerColor
import com.rnazarapps.to_domanager.core.presentation.components.getCompleteButtonColor
import com.rnazarapps.to_domanager.feature_todo.domain.model.TodoItem
import com.rnazarapps.to_domanager.ui.theme.TodoManagerTheme

@Composable
fun TodoItemCard(
    modifier: Modifier = Modifier,
    todoItem: TodoItem,
    onDeleteClick: () -> Unit,
    onCompleteClick: () -> Unit,
    onCardClick: () -> Unit
) {
    val titleContentDescription = stringResource(R.string.todo_title_content_description)
    val textContentDescription = stringResource(R.string.todo_text_content_description)
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        onClick = onCardClick,
        colors = CardDefaults.cardColors(containerColor = getCardContainerColor(todoItem.completed))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = getCardTitleContainerColor(todoItem.completed),
                    shape = MaterialTheme.shapes.large
                )
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            DeleteButton(
                modifier = Modifier.weight(0.1f),
                onDeleteClick = onDeleteClick,
                color = contentColorFor(getCardTitleContainerColor(todoItem.completed))
            )
            Text(
                modifier = Modifier
                    .weight(0.8f)
                    .semantics { this.contentDescription = titleContentDescription },
                textAlign = TextAlign.Center,
                text = todoItem.title,
                color = contentColorFor(getCardTitleContainerColor(todoItem.completed)),
                style = MaterialTheme.typography.headlineMedium,
                fontSize = 26.sp, // todo make changeable
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            CompleteButton(
                modifier = Modifier.weight(0.1f),
                onCompleteClick = onCompleteClick,
                completed = todoItem.completed,
                color = getCompleteButtonColor(completed = todoItem.completed)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .semantics { this.contentDescription = textContentDescription },
                textAlign = TextAlign.Start,
                text = todoItem.text,
                style = MaterialTheme.typography.bodyLarge,
                color = contentColorFor(getCardContainerColor(todoItem.completed)),
                fontSize = 22.sp, // todo make changeable
                maxLines = 5,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 28.sp

            )
        }
    }
}

@PreviewLightDark
@Composable
fun TodoItemCardCompletedPreview(modifier: Modifier = Modifier) {
    TodoManagerTheme {
        TodoItemCard(
            todoItem = TodoItem(
                title = "Task 1",
                text = "Do Task 1 Do Task 1 Do Task 1 Do Task 1 Do Task 1 Do Task 1 Do Task 1 Do" +
                        " Task 1 Do Task 1 Do Task 1 Do Task 1 Do Task 1 Do Task 1 Do Task 1 Do" +
                        " Task 1 Do Task 1 Do Task 1 Do Task 1 Do Task 1 Do Task 1",
                timestamp = 14513406,
                completed = true,
                id = 0
            ),
            onDeleteClick = { },
            onCompleteClick = { },
            onCardClick = { }
        )
    }
}

@PreviewLightDark
@Composable
fun TodoItemCardUncompletedPreview(modifier: Modifier = Modifier) {
    TodoManagerTheme {
        TodoItemCard(
            todoItem = TodoItem(
                title = "Task 1",
                text = "Do Task 1 Do Task 1 Do Task 1 Do Task 1 Do Task 1 Do Task 1 Do Task 1 Do" +
                        " Task 1 Do Task 1 Do Task 1 Do Task 1 Do Task 1 Do Task 1 Do Task 1 Do" +
                        " Task 1 Do Task 1 Do Task 1 Do Task 1 Do Task 1 Do Task 1",
                timestamp = 14513406,
                completed = false,
                id = 0
            ),
            onDeleteClick = { },
            onCompleteClick = { },
            onCardClick = { }
        )
    }
}
