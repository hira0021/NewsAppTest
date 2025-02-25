package com.example.newsapptest.presentation.recent

import androidx.lifecycle.ViewModel
import com.example.newsapptest.domain.usecase.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecentViewModel @Inject constructor(
    val newsInteractor: NewsUseCase
): ViewModel() {
}