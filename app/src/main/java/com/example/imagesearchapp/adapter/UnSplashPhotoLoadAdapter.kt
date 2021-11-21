package com.example.imagesearchapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.example.imagesearchapp.databinding.CellUnsplashFooterBinding

class UnSplashPhotoLoadAdapter(private val retry: () -> Unit) : LoadStateAdapter<UnSplashPhotoLoadViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): UnSplashPhotoLoadViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return UnSplashPhotoLoadViewHolder(CellUnsplashFooterBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: UnSplashPhotoLoadViewHolder, loadState: LoadState) {
        holder.binding.apply {
            isLoading = loadState is LoadState.Loading
            btnRetry.setOnClickListener {
                retry.invoke()
            }
            executePendingBindings()
        }
    }
}