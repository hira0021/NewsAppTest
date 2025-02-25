package com.example.newsapptest.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapptest.R
import com.example.newsapptest.databinding.ItemLoadBinding

class HomePagingLoaderAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<HomePagingLoaderAdapter.HomePagingLoaderViewHolder>() {

    inner class HomePagingLoaderViewHolder(val binding: ItemLoadBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState, retry: () -> Unit) {
            if (loadState is LoadState.Error) {
                binding.tvErrorMessage.setText(R.string.error_occured)
            }
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.btnRetry.isVisible = loadState !is LoadState.Loading
            binding.btnRetry.setOnClickListener {
                retry()
            }
        }
    }

    override fun onBindViewHolder(holder: HomePagingLoaderViewHolder, loadState: LoadState) {
        holder.bind(loadState, retry)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): HomePagingLoaderViewHolder {
        return HomePagingLoaderViewHolder(
            ItemLoadBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}