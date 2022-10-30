package com.example.imagesearchapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.example.imagesearchapp.adapter.viewholder.UnsplashPhotoLoadViewHolder
import com.example.imagesearchapp.databinding.CellUnsplashFooterBinding
import com.example.imagesearchapp.util.executeAfter

class UnsplashPhotoLoadAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<UnsplashPhotoLoadViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): UnsplashPhotoLoadViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return UnsplashPhotoLoadViewHolder(CellUnsplashFooterBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: UnsplashPhotoLoadViewHolder, loadState: LoadState) {
        holder.binding.executeAfter {
            isLoading = loadState is LoadState.Loading
            btnRetry.setOnClickListener {
                retry.invoke()
            }
        }
    }
}