package com.example.newsapptest.data.remote

import com.example.newsapptest.domain.entity.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsServices {

    @GET("v2/everything/")
    suspend fun getNews(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String
    ): NewsResponse

}