package com.example.newsapptest.presentation.newsdetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
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

        window.statusBarColor = getColor(R.color.main_color_dark)

        binding = ActivityNewsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve data from intent
        val articleJson = intent.getStringExtra("ARTICLE_DATA")
        val article = Gson().fromJson(articleJson, Article::class.java)

        // Set Data to Views
        binding.textViewTitle.text = article.title
        binding.textViewName.text = "Source : ${article.source.name}"
        binding.textViewAuthor.text = "By: ${article.author ?: "Anonymous"}"
        binding.textViewPublishedAt.text = formatDate1(article.publishedAt)
        binding.textViewDescription.text = article.description

        // Load Image
        Glide.with(this)
            .load(article.urlToImage)
            .apply(RequestOptions().centerCrop().transform(RoundedCorners(16)))
            .into(binding.imageViewNews)

        // Open URL
        binding.buttonOpenUrl.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
            startActivity(intent)
        }
    }
}
