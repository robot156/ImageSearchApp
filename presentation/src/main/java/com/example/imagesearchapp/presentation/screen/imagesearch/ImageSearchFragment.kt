package com.example.imagesearchapp.presentation.screen.imagesearch

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.imagesearchapp.presentation.R
import com.example.imagesearchapp.presentation.databinding.FragmentImageSearchBinding
import com.example.imagesearchapp.presentation.screen.DataBindingFragment
import com.example.imagesearchapp.presentation.utils.DebounceEditTextListener
import com.example.imagesearchapp.presentation.utils.EventObserver
import com.example.imagesearchapp.presentation.utils.hideKeyboard
import com.example.imagesearchapp.presentation.utils.safeNavigate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ImageSearchFragment : DataBindingFragment<FragmentImageSearchBinding>(R.layout.fragment_image_search) {

    private val imageSearchViewModel: ImageSearchViewModel by viewModels()

    private val searchTextChangeListener by lazy {
        DebounceEditTextListener(
            scope = imageSearchViewModel.viewModelScope,
            onDebounceEditTextChange = imageSearchViewModel::setKeyword,
            debouncePeriod = 0L
        )
    }

    private val searchTextEditTextActionListener by lazy {
        TextView.OnEditorActionListener { textView, actionId, _ ->
            return@OnEditorActionListener if (actionId == EditorInfo.IME_ACTION_DONE && textView.text.trim().isNotEmpty()) {
                imageSearchViewModel.navigateToSearchList(textView.text.toString())
                true
            } else {
                false
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataBinding.apply {
            viewModel = imageSearchViewModel
            lifecycleOwner = viewLifecycleOwner
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
        imageSearchViewModel.navigateToSearchList.observe(viewLifecycleOwner, EventObserver { keyword ->
            lifecycleScope.launch {
                dataBinding.root.hideKeyboard().also {
                    delay(100)
                    findNavController().safeNavigate(ImageSearchFragmentDirections.actionImageSearchFragmentToImageSearchListFragment(keyword))
                }
            }
        })

        imageSearchViewModel.navigateToKeep.observe(viewLifecycleOwner, EventObserver {
            findNavController().safeNavigate(ImageSearchFragmentDirections.actionImageSearchFragmentToImageKeepFragment())
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