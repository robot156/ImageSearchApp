package com.example.imagesearchapp.screen

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
import com.example.imagesearchapp.DataBindingFragment
import com.example.imagesearchapp.R
import com.example.imagesearchapp.adapter.UnsplashPhotoAdapter
import com.example.imagesearchapp.adapter.UnsplashPhotoLoadAdapter
import com.example.imagesearchapp.databinding.FragmentListBinding
import com.example.imagesearchapp.util.EventObserver
import com.example.imagesearchapp.util.hideKeyboard
import com.example.imagesearchapp.util.safeNavigate
import com.example.imagesearchapp.util.setOnMenuItemSingleClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ListFragment : DataBindingFragment<FragmentListBinding>(R.layout.fragment_list) {

    private val listViewModel: ListViewModel by viewModels()

    private val unsplashPhotoAdapter by lazy { UnsplashPhotoAdapter(listViewModel) }

    private val searchTextEditTextActionListener by lazy {
        TextView.OnEditorActionListener { textView, actionId, _ ->
            textView.hideKeyboard(true)
            return@OnEditorActionListener if (actionId == EditorInfo.IME_ACTION_DONE && textView.text.trim().isNotEmpty()) {
                listViewModel.setKeyword(textView.text.toString())
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
                    listViewModel.setSearchMenuVisible(false)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataBinding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = listViewModel
        }

        initView()
        initListener()
        initObserver()
    }

    private fun initView() {
        dataBinding.rvPhoto.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = unsplashPhotoAdapter.withLoadStateHeaderAndFooter(
                header = UnsplashPhotoLoadAdapter { unsplashPhotoAdapter.retry() },
                footer = UnsplashPhotoLoadAdapter { unsplashPhotoAdapter.retry() }
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
                            listViewModel.setSearchMenuVisible(!(listViewModel.isSearchMenuVisible.value ?: false))
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
            listViewModel.photos.collectLatest {
                unsplashPhotoAdapter.submitData(it)
            }
        }

        listViewModel.isSearchMenuVisible.observe(viewLifecycleOwner, Observer {
            if (!it) dataBinding.tietKeyword.hideKeyboard(true)
        })

        listViewModel.clickRefresh.observe(viewLifecycleOwner, EventObserver {
            unsplashPhotoAdapter.retry()
        })

        listViewModel.navigateToDetail.observe(viewLifecycleOwner, EventObserver {
            findNavController().safeNavigate(ListFragmentDirections.actionListFragmentToDetailFragment(listViewModel.keyword.value, it))
        })

        listViewModel.navigateToBack.observe(viewLifecycleOwner, EventObserver {
            findNavController().navigateUp()
        })

        unsplashPhotoAdapter.addLoadStateListener { loadState ->
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