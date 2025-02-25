package com.example.newsapptest.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class HomeAdapter(private var items: List<Article>?, private val isLoading: Boolean) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_LARGE = 0
        private const val TYPE_SMALL = 1
        private const val TYPE_SHIMMER = 2
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            isLoading -> TYPE_SHIMMER
            position % 5 == 0 -> TYPE_LARGE
            else -> TYPE_SMALL
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_SHIMMER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_shimmer, parent, false)
                ShimmerViewHolder(view)
            }
            TYPE_LARGE -> {
                val binding = ItemLargeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                LargeViewHolder(binding)
            }
            else -> {
                val binding = ItemSmallBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                SmallViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (!isLoading && holder is LargeViewHolder) {
            holder.bind(items!![position])
        } else if (!isLoading && holder is SmallViewHolder) {
            holder.bind(items!![position])
        }
    }

    override fun getItemCount(): Int = if (isLoading) 9 else items?.size ?: 0

    class ShimmerViewHolder(view: View) : RecyclerView.ViewHolder(view)

    class LargeViewHolder(private val binding: ItemLargeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Article) {
            binding.textViewTitle.text = item.title
            binding.textViewDescription.text = item.description
            binding.textViewPublishedAt.text = formatDate1(item.publishedAt)
            val author = if (item.author.isNullOrEmpty()) "Anonymous" else item.author
            binding.textViewAuthor.text = binding.root.context.getString(R.string.author_by, author)

            Glide.with(binding.root.context)
                .load(item.urlToImage)
                .apply(RequestOptions().fitCenter().transform(RoundedCorners(16)))
                .into(binding.imageViewNews)

//            binding.buttonReadMore.setOnClickListener {
//                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
//                binding.root.context.startActivity(intent)
//            }
        }
    }

    class SmallViewHolder(private val binding: ItemSmallBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Article) {
            binding.textViewTitle.text = item.title
            binding.textViewDescription.text = item.description
            binding.textViewPublishedAt.text = formatDate2(item.publishedAt)

            Glide.with(binding.root.context)
                .load(item.urlToImage)
                .apply(RequestOptions().fitCenter().transform(RoundedCorners(16)))
                .into(binding.imageViewNews)
        }
    }

}
