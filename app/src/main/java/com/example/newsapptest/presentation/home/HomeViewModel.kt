package com.example.newsapptest.presentation.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapptest.domain.entity.NewsResponse
import com.example.newsapptest.domain.usecase.NewsUseCase
import com.example.newsapptest.utils.BaseResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val newsInteractor: NewsUseCase
) : ViewModel() {

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _newsList: MutableLiveData<BaseResponse<NewsResponse>> = MutableLiveData()
    val newsList: LiveData<BaseResponse<NewsResponse>> = _newsList

    fun getNews() = viewModelScope.launch {
        if (_isLoading.value == true) return@launch

        _isLoading.value = true
        _newsList.value = BaseResponse.Loading

        newsInteractor.getNews(
            query = "Indonesia",
            page = 1,
            pageSize = 20
        )
            .flowOn(Dispatchers.IO)
            .catch { e ->
                _isLoading.value = false
                Log.e("HomeViewModel", e.toString())
            }
            .collect {
                _isLoading.value = false
                _newsList.value = it
            }
    }

}