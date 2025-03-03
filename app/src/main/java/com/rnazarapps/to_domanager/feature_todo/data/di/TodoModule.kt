package com.rnazarapps.to_domanager.feature_todo.data.di

import android.content.Context
import androidx.room.Room
import com.rnazarapps.to_domanager.feature_todo.data.local.TodoDao
import com.rnazarapps.to_domanager.feature_todo.data.local.TodoDatabase
import com.rnazarapps.to_domanager.feature_todo.data.remote.TodoApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

const val fbUrl = "https://todo-manager-2649b-default-rtdb.europe-west1.firebasedatabase.app/"

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

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .baseUrl(fbUrl)
            .build()
    }

    @Provides
    fun provideRetrofitApi(retrofit: Retrofit): TodoApi {
        return retrofit.create(TodoApi::class.java)
    }
}