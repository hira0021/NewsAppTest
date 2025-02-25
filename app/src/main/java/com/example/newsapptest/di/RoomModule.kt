package com.example.newsapptest.di

import android.content.Context
import androidx.room.Room
import com.example.newsapptest.data.local.MyAppDatabase
import com.example.newsapptest.data.local.dao.ArticleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): MyAppDatabase {
        return Room.databaseBuilder(
            context,
            MyAppDatabase::class.java,
            MyAppDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideArticleDao(myAppDatabase: MyAppDatabase): ArticleDao {
        return myAppDatabase.articleDao()
    }
}