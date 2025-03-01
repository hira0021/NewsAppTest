package com.example.newsapptest.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.newsapptest.R
import com.example.newsapptest.databinding.FragmentHomeBinding
import com.example.newsapptest.domain.entity.Article
import com.example.newsapptest.presentation.newsdetail.NewsDetailActivity
import com.google.gson.Gson
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

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        setHasOptionsMenu(true)

        setupRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.pagingNewsList.collectLatest { pagingData ->
                homePagingAdapter.submitData(pagingData)
            }
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false

            binding.shimmerLayout.startShimmer()
            binding.shimmerLayout.visibility = View.VISIBLE

            homePagingAdapter.refresh()
        }

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
                binding.shimmerLayout.startShimmer()
                binding.shimmerLayout.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            } else {
                binding.shimmerLayout.stopShimmer()
                binding.shimmerLayout.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
            }
        }

    }

    private fun listNewsClicked(article: Article) {
        val gson = Gson()
        val articleJson = gson.toJson(article)

        val intent = Intent(requireContext(), NewsDetailActivity::class.java).apply {
            putExtra("ARTICLE_DATA", articleJson)
        }
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_home, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.queryHint = "Search news..."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val imm = requireContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE)
                        as android.view.inputmethod.InputMethodManager
                imm.hideSoftInputFromWindow(view?.windowToken, 0)

                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                query?.let { homeViewModel.updateSearchQuery(it) }
                return false
            }
        })

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}