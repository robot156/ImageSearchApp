package com.example.imagesearchapp.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.imagesearchapp.DataBindingFragment
import com.example.imagesearchapp.R
import com.example.imagesearchapp.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : DataBindingFragment<FragmentDetailBinding>(R.layout.fragment_detail) {

    private val detailViewModel: DetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataBinding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = detailViewModel
        }

        initListener()
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
}