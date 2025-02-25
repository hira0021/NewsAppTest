package com.example.newsapptest.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.newsapptest.R
import com.example.newsapptest.databinding.ItemLargeBinding
import com.example.newsapptest.databinding.ItemSmallBinding
import com.example.newsapptest.domain.entity.Article
import com.example.newsapptest.utils.DateUtils.formatDate1
import com.example.newsapptest.utils.DateUtils.formatDate2

class HomePagingAdapter(
    private val clickListener: (Article) -> Unit,
) : PagingDataAdapter<Article, RecyclerView.ViewHolder>(diffCallback) {

    companion object {
        private const val TYPE_LARGE = 0
        private const val TYPE_SMALL = 1

        val diffCallback = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position % 5 == 0) TYPE_LARGE else TYPE_SMALL
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_LARGE -> {
                val binding =
                    ItemLargeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                LargeViewHolder(binding)
            }

            else -> {
                val binding =
                    ItemSmallBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                SmallViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = getItem(position)

        when (holder) {
            is LargeViewHolder -> holder.bind(currentItem, clickListener)
            is SmallViewHolder -> holder.bind(currentItem, clickListener)
        }
    }

    class LargeViewHolder(private val binding: ItemLargeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article?, clickListener: (Article) -> Unit) {
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
                article?.let { clickListener(it) }
            }
        }
    }

    class SmallViewHolder(private val binding: ItemSmallBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article?, clickListener: (Article) -> Unit) {
            binding.textViewTitle.text = article?.title
            binding.textViewDescription.text = article?.description
            binding.textViewPublishedAt.text = formatDate2(article?.publishedAt)

            Glide.with(binding.root.context)
                .load(article?.urlToImage)
                .apply(RequestOptions().fitCenter().transform(RoundedCorners(16)))
                .into(binding.imageViewNews)

            binding.cardContainer.setOnClickListener {
                article?.let { clickListener(it) }
            }
        }
    }
}
