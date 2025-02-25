package com.example.newsapptest.di

import com.example.newsapptest.domain.repository.INewsRepository
import com.example.newsapptest.domain.usecase.NewsInteractor
import com.example.newsapptest.domain.usecase.NewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideNewsUseCase(repository: INewsRepository): NewsUseCase {
        return NewsInteractor(repository)
    }
}