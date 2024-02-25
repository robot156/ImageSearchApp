package com.example.imagesearchapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.imagesearchapp.presentation.R
import com.example.imagesearchapp.presentation.adapter.viewholder.SearchUnsplashImageViewHolder
import com.example.imagesearchapp.presentation.databinding.ItemSearchUnsplashImageBinding
import com.example.imagesearchapp.presentation.model.UnsplashImageItem
import com.example.imagesearchapp.presentation.screen.imagesearchlist.ImageSearchListViewModel
import com.example.imagesearchapp.presentation.utils.executeAfter

class SearchUnsplashImageAdapter(
    private val imageSearchListViewModel: ImageSearchListViewModel
) : PagingDataAdapter<UnsplashImageItem, SearchUnsplashImageViewHolder>(
    object : DiffUtil.ItemCallback<UnsplashImageItem>() {
        override fun areItemsTheSame(oldItem: UnsplashImageItem, newItem: UnsplashImageItem) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: UnsplashImageItem, newItem: UnsplashImageItem) =
            oldItem == newItem
    }
) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchUnsplashImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SearchUnsplashImageViewHolder(ItemSearchUnsplashImageBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holderUnSplash: SearchUnsplashImageViewHolder, position: Int) {
        getItem(position)?.let {
            holderUnSplash.binding.executeAfter {
                viewModel = imageSearchListViewModel
                lifecycleOwner = holderUnSplash.itemView.findViewTreeLifecycleOwner()
                imageItem = it
            }
        }
    }

    override fun getItemViewType(position: Int) = R.layout.item_search_unsplash_image
}