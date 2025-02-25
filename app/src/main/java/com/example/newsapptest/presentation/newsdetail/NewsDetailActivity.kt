package com.example.newsapptest.presentation.newsdetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.newsapptest.R
import com.example.newsapptest.databinding.ActivityNewsDetailBinding
import com.example.newsapptest.domain.entity.Article
import com.example.newsapptest.utils.DateUtils.formatDate1
import com.google.gson.Gson

class NewsDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val articleJson = intent.getStringExtra("ARTICLE_DATA")
        val article: Article? = Gson().fromJson(articleJson, Article::class.java)

        article?.let {
            displayArticleDetails(it)
        }
    }

    private fun displayArticleDetails(article: Article) {
        binding.textViewTitle.text = article.title
        binding.textViewDescription.text = article.description
        binding.textViewPublishedAt.text = formatDate1(article.publishedAt)

        val author = if (article.author.isNullOrEmpty()) "Anonymous" else article.author
        binding.textViewAuthor.text = getString(R.string.author_by, author)

        Glide.with(this)
            .load(article.urlToImage)
            .apply(RequestOptions().fitCenter().transform(RoundedCorners(16)))
            .into(binding.imageViewNews)

        binding.btnBack.setOnClickListener { finish() }
    }
}