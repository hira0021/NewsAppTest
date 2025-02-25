package com.example.newsapptest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsapptest.data.local.dao.ArticleDao
import com.example.newsapptest.data.local.entity.ArticleEntity

@Database(entities = [ArticleEntity::class], version = 1)
abstract class MyAppDatabase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao

    companion object {
        val DATABASE_NAME: String = "app_db"
    }

}