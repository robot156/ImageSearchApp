package com.example.imagesearchapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.example.imagesearchapp.presentation.adapter.viewholder.UnsplashImageLoadViewHolder
import com.example.imagesearchapp.presentation.databinding.ItemUnsplashFooterBinding
import com.example.imagesearchapp.presentation.utils.executeAfter

class UnsplashImageLoadAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<UnsplashImageLoadViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): UnsplashImageLoadViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return UnsplashImageLoadViewHolder(ItemUnsplashFooterBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: UnsplashImageLoadViewHolder, loadState: LoadState) {
        holder.binding.executeAfter {
            isLoading = loadState is LoadState.Loading
            btnRetry.setOnClickListener {
                retry.invoke()
            }
        }
    }
}