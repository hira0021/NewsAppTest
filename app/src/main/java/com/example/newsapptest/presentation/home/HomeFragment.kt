package com.example.newsapptest.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapptest.databinding.FragmentHomeBinding
import com.example.newsapptest.domain.entity.Article
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var homePagingAdapter: HomePagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.pagingNewsList.collectLatest { pagingData ->
                homePagingAdapter.submitData(pagingData)
            }
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            // Immediately hide the circular loading indicator
            binding.swipeRefreshLayout.isRefreshing = false

            // Show shimmer effect
            binding.shimmerLayout.startShimmer()
            binding.shimmerLayout.visibility = View.VISIBLE

            // Trigger data refresh (this should be your ViewModel call)
            homePagingAdapter.refresh()
        }


//        binding.swipeRefreshLayout.setOnRefreshListener {
//            homePagingAdapter.refresh()
//        }

        homePagingAdapter.addLoadStateListener { loadState ->
            binding.swipeRefreshLayout.isRefreshing = loadState.source.refresh is LoadState.Loading
        }

    }

    private fun setupRecyclerView() {
        binding.recyclerView.setHasFixedSize(true)
        homePagingAdapter = HomePagingAdapter { selectedItem: Article -> listNewsClicked(selectedItem) }

        val layoutManager = GridLayoutManager(requireContext(), 2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position % 5 == 0) 2 else 1
            }
        }

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = homePagingAdapter.withLoadStateHeaderAndFooter(
            header = HomePagingLoaderAdapter { homePagingAdapter.retry() },
            footer = HomePagingLoaderAdapter { homePagingAdapter.retry() }
        )

        homePagingAdapter.addLoadStateListener { loadStates ->
            if (loadStates.refresh is LoadState.Loading) {
                binding.shimmerLayout.startShimmer() // Show shimmer
                binding.shimmerLayout.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            } else {
                binding.shimmerLayout.stopShimmer() // Hide shimmer
                binding.shimmerLayout.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
            }
        }



//            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
//            binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error
//            binding.tvConnectionError.isVisible = loadState.source.refresh is LoadState.Error
//            binding.tvNoResult.isVisible =
//                loadState.refresh is LoadState.NotLoading && homeMovieAdapter.itemCount == 0
//        binding.retryButton.setOnClickListener {
//            homeMovieAdapter.retry()
//        }
    }

    private fun listNewsClicked(article: Article) {
//        selectedMovieDiscoverResult = movieDiscoverResult
//        val intent = Intent(activity, MovieDetailActivity::class.java)
//        intent.putExtra("movieId", selectedMovieDiscoverResult.id)
//        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}