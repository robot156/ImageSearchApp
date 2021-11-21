package com.example.imagesearchapp.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.imagesearchapp.DataBindingFragment
import com.example.imagesearchapp.R
import com.example.imagesearchapp.databinding.FragmentSearchBinding
import com.example.imagesearchapp.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : DataBindingFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val searchViewModel: SearchViewModel by viewModels()

    private val searchTextChangeListener by lazy {
        DebounceEditTextListener(
            scope = searchViewModel.viewModelScope,
            onDebounceEditTextChange = searchViewModel::setKeyword,
            debouncePeriod = 0L
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataBinding.apply {
            lifecycleOwner = this@SearchFragment
            viewModel = searchViewModel
        }

        initListener()
        initObserver()
    }

    private fun initListener() {
        dataBinding.apply {
            etSearch.setOnClickListener {
                it.showKeyboard(true)
            }
        }
    }

    private fun initObserver() {
        searchViewModel.navigateToDetail.observe(viewLifecycleOwner, EventObserver { keyword ->
            lifecycleScope.launch {
                dataBinding.root.hideKeyboard().also {
                    delay(100)
                    findNavController().safeNavigate(SearchFragmentDirections.actionSearchFragmentToListFragment(keyword))
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        dataBinding.etSearch.addTextChangedListener(searchTextChangeListener)
    }

    override fun onPause() {
        dataBinding.etSearch.removeTextChangedListener(searchTextChangeListener)
        super.onPause()
    }
}