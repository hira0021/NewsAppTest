package com.example.newsapptest.presentation.recent

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.newsapptest.domain.entity.ArticleLocal
import com.example.newsapptest.domain.usecase.NewsUseCase
import com.example.newsapptest.utils.BaseResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecentViewModel @Inject constructor(
    val newsInteractor: NewsUseCase
): ViewModel() {

//    private val _savedArticles: MutableLiveData<BaseResponse<List<ArticleLocal>>> = MutableLiveData()
//    val savedArticles: LiveData<BaseResponse<List<ArticleLocal>>> = _savedArticles
//
//    fun getFavoriteMovieListCache() = viewModelScope.launch {
//        newsInteractor.getSavedArticles()
//            .flowOn(Dispatchers.IO)
//            .catch { e ->
//                Log.e("HomeViewModel", e.toString())
//            }
//            .collect {
//                _savedArticles.value = it
//            }
//    }

    val savedArticles: LiveData<List<ArticleLocal>> = newsInteractor.getSavedArticles()
        .asLiveData()

}