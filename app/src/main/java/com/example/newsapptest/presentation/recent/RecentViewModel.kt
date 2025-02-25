package com.example.newsapptest.presentation.recent

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.newsapptest.domain.entity.ArticleLocal
import com.example.newsapptest.domain.usecase.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecentViewModel @Inject constructor(
    val newsInteractor: NewsUseCase
): ViewModel() {

    val savedArticles: LiveData<List<ArticleLocal>> = newsInteractor.getSavedArticles()
        .map { articles -> articles.sortedByDescending { it.savedAt } }
        .asLiveData()

    fun deleteArticle(article: ArticleLocal) {
        viewModelScope.launch {
            newsInteractor.deleteArticle(article)
        }
    }

}