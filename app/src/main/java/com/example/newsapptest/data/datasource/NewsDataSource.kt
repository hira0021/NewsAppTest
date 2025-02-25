package com.example.newsapptest.data.datasource

import com.example.newsapptest.data.local.dao.ArticleDao
import com.example.newsapptest.data.local.entity.ArticleEntity
import com.example.newsapptest.data.mapper.toEntity
import com.example.newsapptest.data.mapper.toLocal
import com.example.newsapptest.data.remote.NewsServices
import com.example.newsapptest.domain.entity.ArticleLocal
import com.example.newsapptest.domain.entity.NewsResponse
import com.example.newsapptest.utils.BaseResponse
import com.example.newsapptest.utils.Const
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsDataSource @Inject constructor(
    val newsServices: NewsServices,
    val articleDao: ArticleDao
) {

    fun getNewsFromDataSource(
        query: String,
        page: Int,
        pageSize: Int
    ): Flow<BaseResponse<NewsResponse>> = flow {
        emit(BaseResponse.Loading)
        try {
            val data = newsServices.getNews(Const.API_KEY, query, page, pageSize)
            emit(BaseResponse.Success(data))
        } catch (e: Exception) {
            emit(BaseResponse.Error(e.message ?: "getFollowingItemFromDataSource Error"))
        }
    }

    suspend fun saveArticle(article: ArticleLocal) {
        articleDao.insertArticle(article.toEntity())
    }

    suspend fun getArticleByTitleAndImage(title: String, urlToImage: String?): ArticleEntity? {
        return articleDao.getArticleByTitleAndImage(title, urlToImage)
    }

    suspend fun updateArticle(article: ArticleEntity) {
        articleDao.updateArticle(article)
    }

    fun getSavedArticles(): Flow<BaseResponse<List<ArticleLocal>>> = flow {
        emit(BaseResponse.Loading)
        try {
            val entities = articleDao.getAllSavedArticles()
            val articles = entities.map { it.toLocal() }
            emit(BaseResponse.Success(articles))
        } catch (e: Exception) {
            emit(BaseResponse.Error(e.toString()))
        }
    }

}