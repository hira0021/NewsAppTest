package com.example.newsapptest.domain.usecase

import androidx.paging.PagingData
import com.example.newsapptest.domain.entity.Article
import com.example.newsapptest.domain.entity.NewsResponse
import com.example.newsapptest.domain.repository.INewsRepository
import com.example.newsapptest.utils.BaseResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsInteractor @Inject constructor(
    val repository: INewsRepository
) : NewsUseCase {

    override suspend fun getNews(query: String, page: Int, pageSize: Int): Flow<BaseResponse<NewsResponse>> =
        repository.getNews(query, page, pageSize)

    override fun getPagerNews(query: String): Flow<PagingData<Article>> =
        repository.getPagingNews(query)

}