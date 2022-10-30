package com.example.imagesearchapp.screen

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.imagesearchapp.DataBindingFragment
import com.example.imagesearchapp.R
import com.example.imagesearchapp.databinding.FragmentSearchBinding
import com.example.imagesearchapp.util.DebounceEditTextListener
import com.example.imagesearchapp.util.EventObserver
import com.example.imagesearchapp.util.hideKeyboard
import com.example.imagesearchapp.util.safeNavigate
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

    private val searchTextEditTextActionListener by lazy {
        TextView.OnEditorActionListener { textView, actionId, _ ->
            return@OnEditorActionListener if (actionId == EditorInfo.IME_ACTION_DONE && textView.text.trim().isNotEmpty()) {
                searchViewModel.navigateToList(textView.text.toString())
                true
            } else {
                false
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataBinding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = searchViewModel
        }

        initListener()
        initObserver()
    }

    private fun initListener() {
        dataBinding.apply {
            tietKeyword.setOnEditorActionListener(searchTextEditTextActionListener)
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

        searchViewModel.navigateToStorage.observe(viewLifecycleOwner, EventObserver {
            findNavController().safeNavigate(SearchFragmentDirections.actionSearchFragmentToImageKeepFragment())
        })
    }

    override fun onResume() {
        super.onResume()
        dataBinding.tietKeyword.addTextChangedListener(searchTextChangeListener)
    }

    override fun onPause() {
        dataBinding.tietKeyword.removeTextChangedListener(searchTextChangeListener)
        super.onPause()
    }

    override fun onDestroyView() {
        dataBinding.tietKeyword.setOnEditorActionListener(null)
        super.onDestroyView()
    }
}