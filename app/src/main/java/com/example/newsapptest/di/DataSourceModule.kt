package com.example.newsapptest.di

import com.example.newsapptest.data.datasource.NewsDataSource
import com.example.newsapptest.data.remote.NewsServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun providesNewsDataSource(newsServices: NewsServices): NewsDataSource {
        return NewsDataSource(newsServices)
    }

}