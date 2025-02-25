package com.example.newsapptest.presentation.home

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.newsapptest.domain.entity.Article
import com.example.newsapptest.domain.usecase.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val newsInteractor: NewsUseCase
) : ViewModel() {

    private val searchQuery = MutableStateFlow("")

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val pagingNewsList: Flow<PagingData<Article>> = searchQuery
        .debounce(800)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            newsInteractor.getPagerNews(query)
        }

    fun updateSearchQuery(query: String) {
        searchQuery.value = query
    }

}