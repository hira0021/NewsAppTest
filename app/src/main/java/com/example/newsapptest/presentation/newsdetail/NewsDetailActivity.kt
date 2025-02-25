package com.example.newsapptest.presentation.newsdetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.newsapptest.R
import com.example.newsapptest.data.mapper.toEntity
import com.example.newsapptest.databinding.ActivityNewsDetailBinding
import com.example.newsapptest.domain.entity.Article
import com.example.newsapptest.domain.entity.ArticleLocal
import com.example.newsapptest.utils.DateUtils.formatDate1
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsDetailBinding
    private val newsDetailViewModel: NewsDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = getColor(R.color.main_color_dark)

        binding = ActivityNewsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "News Detail"

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        val articleJson = intent.getStringExtra("ARTICLE_DATA")
        val article = Gson().fromJson(articleJson, Article::class.java)

        binding.textViewTitle.text = article.title
        binding.textViewName.text = "Source : ${article.source.name}"
        binding.textViewAuthor.text = "By: ${article.author ?: "Anonymous"}"
        binding.textViewPublishedAt.text = formatDate1(article.publishedAt)
        binding.textViewDescription.text = article.description

        Glide.with(this)
            .load(article.urlToImage)
            .apply(RequestOptions().centerCrop().transform(RoundedCorners(16)))
            .into(binding.imageViewNews)

        binding.buttonOpenUrl.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
            startActivity(intent)
        }

        saveArticleToLocal(article)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun saveArticleToLocal(article: Article) {
        val articleLocal = ArticleLocal(
            id = 0,
            title = article.title,
            urlToImage = article.urlToImage,
            url = article.url,
            savedAt = System.currentTimeMillis()
        )

        lifecycleScope.launch {
            newsDetailViewModel.saveArticle(articleLocal)
        }
    }

}
