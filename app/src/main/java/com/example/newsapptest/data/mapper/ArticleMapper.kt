package com.example.newsapptest.data.mapper

import com.example.newsapptest.data.local.entity.ArticleEntity
import com.example.newsapptest.domain.entity.ArticleLocal

fun ArticleEntity.toLocal(): ArticleLocal {
    return ArticleLocal(
        id = this.id,
        title = this.title,
        urlToImage = this.urlToImage,
        url = this.url,
        savedAt = this.savedAt
    )
}

fun ArticleLocal.toEntity(): ArticleEntity {
    return ArticleEntity(
        id = this.id,
        title = this.title,
        urlToImage = this.urlToImage,
        url = this.url,
        savedAt = this.savedAt
    )
}