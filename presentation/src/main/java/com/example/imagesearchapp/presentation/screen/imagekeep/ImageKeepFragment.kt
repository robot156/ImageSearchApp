package com.example.imagesearchapp.presentation.screen.imagekeep

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imagesearchapp.presentation.R
import com.example.imagesearchapp.presentation.adapter.KeepUnsplashImageAdapter
import com.example.imagesearchapp.presentation.databinding.FragmentImageKeepBinding
import com.example.imagesearchapp.presentation.screen.DataBindingFragment
import com.example.imagesearchapp.presentation.utils.EventObserver
import com.example.imagesearchapp.presentation.utils.safeNavigate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ImageKeepFragment : DataBindingFragment<FragmentImageKeepBinding>(R.layout.fragment_image_keep) {

    private val imageKeepViewModel: ImageKeepViewModel by viewModels()

    private val imageKeepAdapter by lazy { KeepUnsplashImageAdapter(imageKeepViewModel) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataBinding.apply {
            viewModel = imageKeepViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        initView()
        initListener()
        initObserver()
    }

    private fun initView() {
        with(dataBinding) {
            rvKeepImage.apply {
                setHasFixedSize(true)
                adapter = imageKeepAdapter
                layoutManager = GridLayoutManager(requireContext(), 3).apply {
                    this.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int {
                            return when (adapter?.getItemViewType(position)) {
                                R.layout.item_keep_unsplash_image_header -> 3
                                else -> 1
                            }
                        }
                    }
                }
            }
        }
    }

    private fun initListener() {
        with(dataBinding) {
            toolbar.apply {
                setNavigationOnClickListener {
                    if (!findNavController().navigateUp()) {
                        requireActivity().finish()
                    }
                }
            }
        }
    }

    private fun initObserver() {
        lifecycleScope.launchWhenStarted {
            launch {
                imageKeepViewModel.keepImages.collectLatest {
                    imageKeepAdapter.submitData(it)
                }
            }

            launch {
                imageKeepAdapter
                    .loadStateFlow
                    .distinctUntilChangedBy { it.refresh }
                    .collect { loadState ->
                        with(dataBinding) {
                            isSuccess = loadState.source.refresh is LoadState.NotLoading
                            isLoading = loadState.source.refresh is LoadState.Loading
                            isError = loadState.source.refresh is LoadState.Error
                        }
                    }
            }
        }

        imageKeepViewModel.navigateToDetail.observe(viewLifecycleOwner, EventObserver {
            findNavController().safeNavigate(ImageKeepFragmentDirections.actionImageKeepFragmentToImageDetailFragment(keyword = it.keyword, imageData = it))
        })
    }

    override fun onDestroyView() {
        dataBinding.rvKeepImage.adapter = null
        super.onDestroyView()
    }
}