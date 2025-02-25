package com.example.newsapptest.domain.repository

import androidx.paging.PagingData
import com.example.newsapptest.domain.entity.Article
import com.example.newsapptest.domain.entity.ArticleLocal
import com.example.newsapptest.domain.entity.NewsResponse
import com.example.newsapptest.utils.BaseResponse
import kotlinx.coroutines.flow.Flow

interface INewsRepository {

    suspend fun getNews(query: String, page: Int, pageSize: Int): Flow<BaseResponse<NewsResponse>>

    fun getPagingNews(query: String): Flow<PagingData<Article>>

    suspend fun saveArticle(article: ArticleLocal)

    fun getSavedArticles() : Flow<List<ArticleLocal>>

}