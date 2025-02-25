package com.example.newsapptest.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newsapptest.data.datasource.NewsDataSource
import com.example.newsapptest.data.mapper.toLocal
import com.example.newsapptest.data.pagingdatasource.NewsPagingDataSource
import com.example.newsapptest.domain.entity.Article
import com.example.newsapptest.domain.entity.ArticleLocal
import com.example.newsapptest.domain.entity.NewsResponse
import com.example.newsapptest.domain.repository.INewsRepository
import com.example.newsapptest.utils.BaseResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsRepository @Inject constructor(
    val newsDataSource: NewsDataSource,
    val newsPagingDataSource: NewsPagingDataSource
) : INewsRepository {

    override suspend fun getNews(query: String, page: Int, pageSize: Int): Flow<BaseResponse<NewsResponse>> {
        return newsDataSource.getNewsFromDataSource(query, page, pageSize)
    }

    override fun getPagingNews(query: String): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { NewsPagingDataSource(newsDataSource.newsServices).apply { getSearchQuery(query) } }
        ).flow
    }

    override suspend fun saveArticle(article: ArticleLocal) {
        val existingArticle = newsDataSource.getArticleByTitleAndImage(article.title, article.urlToImage)

        if (existingArticle != null) {
            val updatedArticle = existingArticle.copy(savedAt = System.currentTimeMillis())
            newsDataSource.updateArticle(updatedArticle)
        } else {
            newsDataSource.saveArticle(article)
        }
    }

    override suspend fun getSavedArticles(): Flow<BaseResponse<List<ArticleLocal>>> {
        return newsDataSource.getSavedArticles()
    }

}