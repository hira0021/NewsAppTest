package com.example.newsapptest.di

import com.example.newsapptest.data.datasource.NewsDataSource
import com.example.newsapptest.data.pagingdatasource.NewsPagingDataSource
import com.example.newsapptest.data.repository.NewsRepository
import com.example.newsapptest.domain.repository.INewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideNewsRepository(dataSource: NewsDataSource, pagingSource: NewsPagingDataSource): INewsRepository {
        return NewsRepository(dataSource, pagingSource)
    }

}