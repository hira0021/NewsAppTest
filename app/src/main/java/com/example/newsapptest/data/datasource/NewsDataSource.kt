package com.example.newsapptest.data.datasource

import com.example.newsapptest.data.remote.NewsServices
import com.example.newsapptest.domain.entity.NewsResponse
import com.example.newsapptest.utils.BaseResponse
import com.example.newsapptest.utils.Const
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsDataSource @Inject constructor(
    val newsServices: NewsServices
) {

    suspend fun getNewsFromDataSource(): Flow<BaseResponse<NewsResponse>> = flow {
        emit(BaseResponse.Loading)
        try {
            val data = newsServices.getNews(
                query = "Indonesia",
                apiKey = Const.API_KEY
            )
            emit(BaseResponse.Success(data))
        } catch (e: Exception) {
            emit(BaseResponse.Error(e.message ?: "getFollowingItemFromDataSource Error"))
        }
    }

}