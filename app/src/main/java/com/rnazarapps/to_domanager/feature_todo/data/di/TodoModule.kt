package com.rnazarapps.to_domanager.feature_todo.data.di

import android.content.Context
import androidx.room.Room
import com.rnazarapps.to_domanager.feature_todo.data.local.TodoDao
import com.rnazarapps.to_domanager.feature_todo.data.local.TodoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TodoModule {
    @Provides
    fun provideTodoDao(database: TodoDatabase): TodoDao {
        return database.dao
    }

    @Singleton
    @Provides
    fun provideTodoDatabase(
        @ApplicationContext
        context: Context
    ): TodoDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = TodoDatabase::class.java,
            name = "todo_database"
        ).fallbackToDestructiveMigration().build()
    }
}