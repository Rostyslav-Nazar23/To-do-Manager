package com.rnazarapps.to_domanager.feature_todo.data.mapper

import com.rnazarapps.to_domanager.feature_todo.data.local.dto.LocalTodoItem
import com.rnazarapps.to_domanager.feature_todo.data.remote.dto.RemoteTodoItem
import com.rnazarapps.to_domanager.feature_todo.domain.model.TodoItem

fun TodoItem.toRemoteTodoItem(): RemoteTodoItem {
    return RemoteTodoItem(
        title = title,
        text = text,
        timestamp = timestamp,
        completed = completed,
        id = id!!
    )
}

fun RemoteTodoItem.toTodoItem(): TodoItem {
    return TodoItem(
        title = title,
        text = text,
        timestamp = timestamp,
        completed = completed,
        id = id
    )
}

fun TodoItem.toLocalTodoItem(): LocalTodoItem {
    return LocalTodoItem(
        title = title,
        text = text,
        timestamp = timestamp,
        completed = completed,
        id = id!!
    )
}

fun LocalTodoItem.toTodoItem(): TodoItem {
    return TodoItem(
        title = title,
        text = text,
        timestamp = timestamp,
        completed = completed,
        id = id
    )
}

fun RemoteTodoItem.toLocalTodoItem(): LocalTodoItem {
    return LocalTodoItem(
        title = title,
        text = text,
        timestamp = timestamp,
        completed = completed,
        id = id
    )
}

fun LocalTodoItem.toRemoteTodoItem(): RemoteTodoItem {
    return RemoteTodoItem(
        title = title,
        text = text,
        timestamp = timestamp,
        completed = completed,
        id = id
    )
}

fun List<LocalTodoItem>.toTodoItemListFromLocal(): List<TodoItem> {
    return this.map { todo ->
        TodoItem(
            title = todo.title,
            text = todo.text,
            timestamp = todo.timestamp,
            completed = todo.completed,
            id = todo.id
        )
    }
}

fun List<TodoItem>.toLocalTodoItemList(): List<LocalTodoItem> {
    return this.map { todo ->
        LocalTodoItem(
            title = todo.title,
            text = todo.text,
            timestamp = todo.timestamp,
            completed = todo.completed,
            id = todo.id!!
        )
    }
}

fun List<RemoteTodoItem>.toTodoItemListFromRemote(): List<TodoItem> {
    return this.map { todo ->
        TodoItem(
            title = todo.title,
            text = todo.text,
            timestamp = todo.timestamp,
            completed = todo.completed,
            id = todo.id
        )
    }
}

fun List<TodoItem>.toRemoteTodoItemList(): List<RemoteTodoItem> {
    return this.map { todo ->
        RemoteTodoItem(
            title = todo.title,
            text = todo.text,
            timestamp = todo.timestamp,
            completed = todo.completed,
            id = todo.id!!
        )
    }
}

fun List<RemoteTodoItem>.toLocalItemListFromRemote(): List<LocalTodoItem> {
    return this.map { todo ->
        LocalTodoItem(
            title = todo.title,
            text = todo.text,
            timestamp = todo.timestamp,
            completed = todo.completed,
            id = todo.id
        )
    }
}

fun List<LocalTodoItem>.toRemoteItemListFromLocal(): List<RemoteTodoItem> {
    return this.map { todo ->
        RemoteTodoItem(
            title = todo.title,
            text = todo.text,
            timestamp = todo.timestamp,
            completed = todo.completed,
            id = todo.id
        )
    }
}
