package com.example.newsapptest.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.newsapptest.data.local.entity.ArticleEntity

@Dao
interface ArticleDao {

    @Query("SELECT * FROM articles WHERE title = :title AND urlToImage = :urlToImage LIMIT 1")
    suspend fun getArticleByTitleAndImage(title: String, urlToImage: String?): ArticleEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: ArticleEntity)

    @Update
    suspend fun updateArticle(article: ArticleEntity)

}
