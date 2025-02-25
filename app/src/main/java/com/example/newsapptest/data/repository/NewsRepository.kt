package com.example.newsapptest.data.repository

import com.example.newsapptest.data.datasource.NewsDataSource
import com.example.newsapptest.domain.entity.NewsResponse
import com.example.newsapptest.domain.repository.INewsRepository
import com.example.newsapptest.utils.BaseResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepository @Inject constructor(
    val newsDataSource: NewsDataSource
) : INewsRepository {

    override suspend fun getNews(query: String, page: Int, pageSize: Int): Flow<BaseResponse<NewsResponse>> {
        return newsDataSource.getNewsFromDataSource(query, page, pageSize)
    }

}