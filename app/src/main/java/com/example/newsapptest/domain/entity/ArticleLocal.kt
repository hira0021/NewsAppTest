package com.example.newsapptest.domain.entity

data class ArticleLocal(
    val id: Int,
    val title: String,
    val urlToImage: String?,
    val url: String,
    val savedAt: Long
)
