package com.example.newsapptest.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.newsapptest.databinding.FragmentHomeBinding
import com.example.newsapptest.utils.BaseResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var adapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = HomeAdapter()
        setupRecyclerView()

        binding.swipeRefreshLayout.setOnRefreshListener {
            refreshNews()
        }

        refreshNews()
        homeViewModel.newsList.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is BaseResponse.Loading -> {
                    Log.d("HomeFragment", "Loading...")
                    adapter.setLoading(true)
                }
                is BaseResponse.Success -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    adapter.setLoading(false)
                    adapter.setData(response.data.articles)
                }
                is BaseResponse.Error -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    Log.e("HomeFragment", "Error: ${response.message}")
                }
            }
        })
    }

    private fun setupRecyclerView() {
        val layoutManager = GridLayoutManager(requireContext(), 2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when {
                    adapter.isLoading() -> if (position % 5 == 0) 2 else 1
                    position % 5 == 0 -> 2
                    else -> 1
                }
            }
        }

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
    }

    private fun refreshNews() {
        homeViewModel.getNews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}