package com.example.newsapptest.presentation.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.newsapptest.R
import com.example.newsapptest.databinding.ItemLargeBinding
import com.example.newsapptest.domain.entity.Article
import com.example.newsapptest.utils.DateUtils.formatDate1

class HomePagingAdapter(
    private val clickListener: (Article) -> Unit,
) : PagingDataAdapter<Article, HomePagingAdapter.HomePagingViewHolder>(diffCallback) {

    inner class HomePagingViewHolder(val binding: ItemLargeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(context: Context, article: Article?, clickListener: (Article) -> Unit) {
            binding.apply {
                binding.textViewTitle.text = article?.title
                binding.textViewDescription.text = article?.description
                binding.textViewPublishedAt.text = formatDate1(article?.publishedAt)
                val author = if (article?.author.isNullOrEmpty()) "Anonymous" else article?.author
                binding.textViewAuthor.text =
                    binding.root.context.getString(R.string.author_by, author)

                Glide.with(binding.root.context)
                    .load(article?.urlToImage)
                    .apply(RequestOptions().fitCenter().transform(RoundedCorners(16)))
                    .into(binding.imageViewNews)

                binding.cardContainer.setOnClickListener {
                    Toast.makeText(binding.root.context, "Clicked", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: HomePagingViewHolder, position: Int) {
        val context = holder.itemView.context
        val currentItem = getItem(position)

        holder.bind(context, currentItem, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePagingViewHolder {
        return HomePagingViewHolder(
            ItemLargeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}