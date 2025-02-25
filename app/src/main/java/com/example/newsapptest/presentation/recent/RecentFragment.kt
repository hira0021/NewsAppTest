package com.example.newsapptest.presentation.recent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapptest.databinding.FragmentRecentBinding
import com.example.newsapptest.utils.BaseResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecentFragment : Fragment() {

    private var _binding: FragmentRecentBinding? = null
    private val binding get() = _binding!!

    private val recentViewModel: RecentViewModel by viewModels()
    private lateinit var recentArticleAdapter: RecentArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()

        recentViewModel.getFavoriteMovieListCache()
    }

    private fun setupRecyclerView() {
        recentArticleAdapter = RecentArticleAdapter()
        binding.recyclerViewSavedArticles.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recentArticleAdapter
        }
    }

    private fun observeViewModel() {
        recentViewModel.savedArticles.observe(viewLifecycleOwner) { response ->
            when (response) {
                is BaseResponse.Success -> {
                    recentArticleAdapter.submitList(response.data)
                }
                is BaseResponse.Error -> {
                    // Handle error
                }
                is BaseResponse.Loading -> {
                    // Show loading indicator
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}