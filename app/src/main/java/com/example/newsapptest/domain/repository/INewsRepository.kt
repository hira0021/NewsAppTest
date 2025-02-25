package com.example.newsapptest.domain.repository

import com.example.newsapptest.domain.entity.NewsResponse
import com.example.newsapptest.utils.BaseResponse
import kotlinx.coroutines.flow.Flow

interface INewsRepository {

    suspend fun getNews(query: String, page: Int, pageSize: Int): Flow<BaseResponse<NewsResponse>>

}