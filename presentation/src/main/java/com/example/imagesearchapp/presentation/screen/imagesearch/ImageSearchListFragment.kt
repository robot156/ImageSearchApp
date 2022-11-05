package com.example.imagesearchapp.presentation.screen.imagesearch

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesearchapp.presentation.R
import com.example.imagesearchapp.presentation.adapter.SearchUnsplashImageAdapter
import com.example.imagesearchapp.presentation.adapter.UnsplashImageLoadAdapter
import com.example.imagesearchapp.presentation.databinding.FragmentImageSearchListBinding
import com.example.imagesearchapp.presentation.screen.DataBindingFragment
import com.example.imagesearchapp.presentation.utils.EventObserver
import com.example.imagesearchapp.presentation.utils.hideKeyboard
import com.example.imagesearchapp.presentation.utils.safeNavigate
import com.example.imagesearchapp.presentation.utils.setOnMenuItemSingleClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ImageSearchListFragment : DataBindingFragment<FragmentImageSearchListBinding>(R.layout.fragment_image_search_list) {

    private val imageSearchListViewModel: ImageSearchListViewModel by viewModels()

    private val searchUnsplashImageAdapter by lazy { SearchUnsplashImageAdapter(imageSearchListViewModel) }

    private val searchTextEditTextActionListener by lazy {
        TextView.OnEditorActionListener { textView, actionId, _ ->
            textView.hideKeyboard(true)
            return@OnEditorActionListener if (actionId == EditorInfo.IME_ACTION_DONE && textView.text.trim().isNotEmpty()) {
                imageSearchListViewModel.setKeyword(textView.text.toString())
                true
            } else {
                false
            }
        }
    }

    private val searchRecyclerViewScrollStateListener by lazy {
        object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    recyclerView.hideKeyboard(true)
                    imageSearchListViewModel.setSearchMenuVisible(false)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataBinding.apply {
            viewModel = imageSearchListViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        initView()
        initListener()
        initObserver()
    }

    private fun initView() {
        dataBinding.rvPhoto.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = searchUnsplashImageAdapter.withLoadStateHeaderAndFooter(
                header = UnsplashImageLoadAdapter { searchUnsplashImageAdapter.retry() },
                footer = UnsplashImageLoadAdapter { searchUnsplashImageAdapter.retry() }
            )
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

                setOnMenuItemSingleClickListener {
                    when (it.itemId) {
                        R.id.menu_search -> {
                            imageSearchListViewModel.setSearchMenuVisible(!(imageSearchListViewModel.isSearchMenuVisible.value ?: false))
                            true
                        }
                        else -> false
                    }
                }
            }

            tietKeyword.setOnEditorActionListener(searchTextEditTextActionListener)
            rvPhoto.addOnScrollListener(searchRecyclerViewScrollStateListener)
        }
    }

    private fun initObserver() {
        lifecycleScope.launchWhenCreated {
            imageSearchListViewModel.images.collectLatest {
                searchUnsplashImageAdapter.submitData(it)
            }
        }

        imageSearchListViewModel.isSearchMenuVisible.observe(viewLifecycleOwner, Observer {
            if (!it) dataBinding.tietKeyword.hideKeyboard(true)
        })

        imageSearchListViewModel.clickRefresh.observe(viewLifecycleOwner, EventObserver {
             searchUnsplashImageAdapter.retry()
        })

        imageSearchListViewModel.navigateToDetail.observe(viewLifecycleOwner, EventObserver {
            findNavController().safeNavigate(ImageSearchListFragmentDirections.actionImageSearchListFragmentToDetailFragment(imageSearchListViewModel.keyword.value, it))
        })

        imageSearchListViewModel.navigateToBack.observe(viewLifecycleOwner, EventObserver {
            findNavController().navigateUp()
        })

        searchUnsplashImageAdapter.addLoadStateListener { loadState ->
            dataBinding.apply {
                isSuccess = loadState.source.refresh is LoadState.NotLoading
                isLoading = loadState.source.refresh is LoadState.Loading
                isError = loadState.source.refresh is LoadState.Error
            }
        }
    }

    override fun onDestroyView() {
        dataBinding.apply {
            rvPhoto.removeOnScrollListener(searchRecyclerViewScrollStateListener)
            rvPhoto.adapter = null
            tietKeyword.setOnEditorActionListener(null)
        }
        super.onDestroyView()
    }
}