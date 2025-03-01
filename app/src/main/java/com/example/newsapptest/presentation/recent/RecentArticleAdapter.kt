package com.example.newsapptest.presentation.recent

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapptest.databinding.ItemArticleBinding
import com.example.newsapptest.domain.entity.ArticleLocal

class RecentArticleAdapter(
    private val onDeleteClick: (ArticleLocal) -> Unit
) : RecyclerView.Adapter<RecentArticleAdapter.ArticleViewHolder>() {

    private val articles = mutableListOf<ArticleLocal>()

    fun submitList(newList: List<ArticleLocal>) {
        articles.clear()
        articles.addAll(newList.sortedByDescending { it.savedAt })
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(articles[position])
    }

    override fun getItemCount(): Int = articles.size

    inner class ArticleViewHolder(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: ArticleLocal) {
            binding.textViewTitle.text = article.title
            binding.textViewUrl.text = article.url

            Glide.with(binding.imageView)
                .load(article.urlToImage)
                .into(binding.imageView)

            binding.root.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                it.context.startActivity(intent)
            }

            binding.btnDelete.setOnClickListener {
                onDeleteClick(article)
            }

        }
    }

}