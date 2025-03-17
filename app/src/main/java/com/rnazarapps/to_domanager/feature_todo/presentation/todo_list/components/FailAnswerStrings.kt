package com.rnazarapps.to_domanager.feature_todo.presentation.todo_list.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.stringResource
import com.rnazarapps.to_domanager.R
import com.rnazarapps.to_domanager.feature_todo.domain.use_case.Answer
import com.rnazarapps.to_domanager.feature_todo.domain.use_case.AnswerException

@Composable
@ReadOnlyComposable
fun getFailText(answer: Answer.Fail): String {
    return when(answer.exception) {
        is AnswerException.InvalidTodoItem -> stringResource(R.string.invalid_todo_item_exception_text)
    }
}