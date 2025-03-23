package com.rnazarapps.to_domanager.feature_todo.presentation.todo_list.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.stringResource
import com.rnazarapps.to_domanager.R
import com.rnazarapps.to_domanager.feature_todo.data.repo.RepoException

@Composable
@ReadOnlyComposable
fun getRepoFailText(repoException: RepoException): String {
    return when(repoException) {
        is RepoException.NullItemResponse -> stringResource(R.string.todo_item_not_found)
        is RepoException.ConnectionError -> stringResource(R.string.no_internet_or_remote_dont_work)
        is RepoException.DatabaseLocked -> stringResource(R.string.database_is_being_used)
        is RepoException.DiskIOException -> stringResource(R.string.database_locked)
        is RepoException.InternetError -> stringResource(R.string.cant_connect_to_remote_database)
    }
}
