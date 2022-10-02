package com.example.imagesearchapp.screen.imagekeep

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imagesearchapp.DataBindingFragment
import com.example.imagesearchapp.R
import com.example.imagesearchapp.adapter.UnsplashKeepPhotoAdapter
import com.example.imagesearchapp.databinding.FragmentImageKeepBinding
import com.example.imagesearchapp.util.EventObserver
import com.example.imagesearchapp.util.safeNavigate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ImageKeepFragment : DataBindingFragment<FragmentImageKeepBinding>(R.layout.fragment_image_keep) {

    private val imageKeepViewModel: ImageKeepViewModel by viewModels()

    private val unsplashKeepPhotoAdapter by lazy { UnsplashKeepPhotoAdapter(imageKeepViewModel) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(dataBinding) {
            lifecycleOwner = viewLifecycleOwner
            viewModel = imageKeepViewModel
        }

        initView()
        initListener()
        initObserver()
    }

    private fun initView() {
        with(dataBinding) {
            rvKeepImage.apply {
                setHasFixedSize(true)
                adapter = unsplashKeepPhotoAdapter
                layoutManager = GridLayoutManager(requireContext(), 3)
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
                    unsplashKeepPhotoAdapter.submitData(it)
                }
            }

            launch {
                unsplashKeepPhotoAdapter
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
            findNavController().safeNavigate(ImageKeepFragmentDirections.actionImageKeepFragmentToDetailFragment(keyword = resources.getString(R.string.image_keep_title), photoData = it))
        })
    }

    override fun onDestroyView() {
        dataBinding.rvKeepImage.adapter = null
        super.onDestroyView()
    }
}