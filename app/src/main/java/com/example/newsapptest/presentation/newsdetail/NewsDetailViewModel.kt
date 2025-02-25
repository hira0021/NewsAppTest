package com.example.newsapptest.presentation.newsdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapptest.domain.entity.ArticleLocal
import com.example.newsapptest.domain.usecase.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    val newsInteractor: NewsUseCase
) : ViewModel() {

    fun saveArticle(article: ArticleLocal) {
        viewModelScope.launch {
            newsInteractor.saveArticle(article)
        }
    }


}