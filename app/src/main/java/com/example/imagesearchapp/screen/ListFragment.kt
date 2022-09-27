package com.example.imagesearchapp.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imagesearchapp.DataBindingFragment
import com.example.imagesearchapp.R
import com.example.imagesearchapp.adapter.UnSplashPhotoAdapter
import com.example.imagesearchapp.adapter.UnSplashPhotoLoadAdapter
import com.example.imagesearchapp.databinding.FragmentListBinding
import com.example.imagesearchapp.util.EventObserver
import com.example.imagesearchapp.util.safeNavigate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ListFragment : DataBindingFragment<FragmentListBinding>(R.layout.fragment_list) {

    private val listViewModel: ListViewModel by viewModels()

    private val listAdapter by lazy { UnSplashPhotoAdapter(listViewModel) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataBinding.apply {
            viewModel = listViewModel
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
            adapter = listAdapter.withLoadStateHeaderAndFooter(
                header = UnSplashPhotoLoadAdapter { listAdapter.retry() },
                footer = UnSplashPhotoLoadAdapter { listAdapter.retry() }
            )
        }
    }

    private fun initListener() {
        dataBinding.toolbar.apply {
            setNavigationOnClickListener {
                if (!findNavController().navigateUp()) {
                    requireActivity().finish()
                }
            }
        }
    }

    private fun initObserver() {
        lifecycleScope.launchWhenCreated {
            listViewModel.photos.collectLatest {
                listAdapter.submitData(it)
            }
        }

        listViewModel.clickRefresh.observe(viewLifecycleOwner, EventObserver {
            listAdapter.retry()
        })

        listViewModel.navigateToDetail.observe(viewLifecycleOwner, EventObserver {
            findNavController().safeNavigate(ListFragmentDirections.actionListFragmentToDetailFragment(listViewModel.keyword, it))
        })

        listAdapter.addLoadStateListener { loadState ->
            dataBinding.apply {
                isSuccess = loadState.source.refresh is LoadState.NotLoading
                isLoading = loadState.source.refresh is LoadState.Loading
                isError = loadState.source.refresh is LoadState.Error // error 면 true
            }
        }
    }

    override fun onDestroyView() {
        dataBinding.rvPhoto.adapter = null
        super.onDestroyView()
    }
}