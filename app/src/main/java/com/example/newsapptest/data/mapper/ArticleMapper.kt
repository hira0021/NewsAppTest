package com.example.newsapptest.data.mapper

import com.example.newsapptest.data.local.entity.ArticleEntity
import com.example.newsapptest.domain.entity.Article
import com.example.newsapptest.domain.entity.ArticleLocal

fun ArticleEntity.toDomain(): ArticleLocal {
    return ArticleLocal(
        id = this.id,
        title = this.title,
        urlToImage = this.urlToImage,
        url = this.url
    )
}

fun ArticleLocal.toEntity(): ArticleEntity {
    return ArticleEntity(
        id = this.id,
        title = this.title,
        urlToImage = this.urlToImage,
        url = this.url
    )
}

fun Article.toEntity(): ArticleEntity {
    return ArticleEntity(
        title = this.title,
        urlToImage = this.urlToImage,
        url = this.url
    )
}