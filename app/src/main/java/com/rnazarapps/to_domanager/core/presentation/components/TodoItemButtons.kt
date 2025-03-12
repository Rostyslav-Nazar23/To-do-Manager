package com.rnazarapps.to_domanager.core.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rnazarapps.to_domanager.R

@Composable
fun CompleteButton(
    modifier: Modifier = Modifier,
    onCompleteClick: () -> Unit,
    completed: Boolean,
    color: Color,
) {
    IconButton(
        onClick = { onCompleteClick() },
        modifier = modifier
    ) {
        if (completed) {
            Icon(
                imageVector = Icons.Default.CheckCircleOutline,
                tint = color,
                contentDescription = getCompleteContentDescription(completed = true),
                modifier = Modifier.size(48.dp)
            )
        } else {
            CheckCircle(color = contentColorFor(getCardTitleContainerColor(completed = false)))
        }

    }
}

@Composable
fun CheckCircle(color: Color) {
    Canvas(modifier = Modifier.fillMaxSize()) {

        drawCircle(
            color = color,
            style = Stroke(width = 9f),
            radius = (this.size.minDimension * 0.75f) / 2
        )
    }
}

@Composable
fun DeleteButton(
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit,
    color: Color,
) {
    IconButton(
        onClick = { onDeleteClick() },
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            tint = color,
            contentDescription = stringResource(R.string.delete),
            modifier = Modifier.size(48.dp)
        )
    }
}
