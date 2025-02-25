package com.example.newsapptest.data.pagingdatasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsapptest.data.remote.NewsServices
import com.example.newsapptest.domain.entity.Article
import com.example.newsapptest.utils.Const
import java.io.IOException
import javax.inject.Inject

class NewsPagingDataSource @Inject constructor(
    val newsServices: NewsServices
) : PagingSource<Int, Article>() {

    var searchQuery = ""

    companion object {
        private const val STARTING_PAGE_INDEX = 1
        private const val PAGE_SIZE = 20
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = if (searchQuery == "") {
                newsServices.getNews(Const.API_KEY, "Indonesia", position, PAGE_SIZE)
            } else {
                newsServices.getNews(Const.API_KEY, searchQuery, position, PAGE_SIZE)
            }
            val repos = response.articles
            val totalResults = response.totalResults

            val nextPage = if ((position * PAGE_SIZE) >= totalResults || (position * PAGE_SIZE) >= 100) {
                null
            } else {
                position + 1
            }

            LoadResult.Page(
                data = repos,
                prevKey = if (position == 1) null else -1,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (e: IOException) {
            LoadResult.Error(e)
        }
    }

    fun getSearchQuery(query: String) {
        searchQuery = query
    }
}