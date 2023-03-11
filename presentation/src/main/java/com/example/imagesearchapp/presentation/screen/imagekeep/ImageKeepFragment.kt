package com.example.imagesearchapp.presentation.screen.imagekeep

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imagesearchapp.presentation.R
import com.example.imagesearchapp.presentation.adapter.KeepUnsplashImageAdapter
import com.example.imagesearchapp.presentation.databinding.FragmentImageKeepBinding
import com.example.imagesearchapp.presentation.screen.DataBindingFragment
import com.example.imagesearchapp.presentation.utils.launchAndRepeatWithViewLifecycle
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
        launchAndRepeatWithViewLifecycle {
            launch {
                imageKeepViewModel.keepImages.collectLatest {
                    imageKeepAdapter.submitData(it)
                }
            }
            launch {
                imageKeepAdapter.loadStateFlow
                    .distinctUntilChangedBy { it.refresh }
                    .collect { loadState ->
                        with(dataBinding) {
                            isSuccess = loadState.source.refresh is LoadState.NotLoading
                            isLoading = loadState.source.refresh is LoadState.Loading
                            isError = loadState.source.refresh is LoadState.Error

                            clEmptyStorage.isVisible = loadState.refresh is LoadState.NotLoading && imageKeepAdapter.itemCount == 0
                        }
                    }
            }

            launch {
                imageKeepViewModel.navigateToDetail.collect {
                    findNavController().safeNavigate(ImageKeepFragmentDirections.actionImageKeepFragmentToImageDetailFragment(keyword = it.keyword, imageData = it))
                }
            }
        }
    }

    override fun onDestroyView() {
        dataBinding.rvKeepImage.adapter = null
        super.onDestroyView()
    }
}