package com.example.newsapptest.domain.usecase

import com.example.newsapptest.domain.entity.NewsResponse
import com.example.newsapptest.utils.BaseResponse
import kotlinx.coroutines.flow.Flow

interface NewsUseCase {

    suspend fun getNews(): Flow<BaseResponse<NewsResponse>>

}