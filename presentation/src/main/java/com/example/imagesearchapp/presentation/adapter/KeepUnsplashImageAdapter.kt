package com.example.imagesearchapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesearchapp.presentation.R
import com.example.imagesearchapp.presentation.adapter.viewholder.KeepUnsplashImageHeaderViewHolder
import com.example.imagesearchapp.presentation.adapter.viewholder.KeepUnsplashImageViewHolder
import com.example.imagesearchapp.presentation.databinding.ItemKeepUnsplashImageBinding
import com.example.imagesearchapp.presentation.databinding.ItemKeepUnsplashImageHeaderBinding
import com.example.imagesearchapp.presentation.screen.imagekeep.ImageKeepViewModel
import com.example.imagesearchapp.presentation.screen.imagekeep.KeepUnsplashImageItemUiModel
import com.example.imagesearchapp.presentation.utils.executeAfter

class KeepUnsplashImageAdapter(
    private val imageKeepViewModel: ImageKeepViewModel
) : PagingDataAdapter<KeepUnsplashImageItemUiModel, RecyclerView.ViewHolder>(
    object : DiffUtil.ItemCallback<KeepUnsplashImageItemUiModel>() {
        override fun areItemsTheSame(oldItem: KeepUnsplashImageItemUiModel, newItem: KeepUnsplashImageItemUiModel): Boolean {
            return (oldItem is KeepUnsplashImageItemUiModel.KeepUnsplashImageHeaderItem && newItem is KeepUnsplashImageItemUiModel.KeepUnsplashImageHeaderItem && oldItem.keyword == newItem.keyword) ||
                    (oldItem is KeepUnsplashImageItemUiModel.KeepUnsplashImageItem && newItem is KeepUnsplashImageItemUiModel.KeepUnsplashImageItem && oldItem.unsplashImageItem.id == newItem.unsplashImageItem.id)
        }

        override fun areContentsTheSame(oldItem: KeepUnsplashImageItemUiModel, newItem: KeepUnsplashImageItemUiModel): Boolean {
            return oldItem == newItem
        }
    }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.item_keep_unsplash_image_header -> KeepUnsplashImageHeaderViewHolder(ItemKeepUnsplashImageHeaderBinding.inflate(inflater, parent, false))
            R.layout.item_keep_unsplash_image -> KeepUnsplashImageViewHolder(ItemKeepUnsplashImageBinding.inflate(inflater, parent, false))
            else -> throw IllegalStateException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val uiModel = getItem(position)) {
            is KeepUnsplashImageItemUiModel.KeepUnsplashImageHeaderItem -> (holder as KeepUnsplashImageHeaderViewHolder).binding.executeAfter {
                lifecycleOwner = holder.itemView.findViewTreeLifecycleOwner()
                viewModel = imageKeepViewModel
                keyword = uiModel.keyword
            }
            is KeepUnsplashImageItemUiModel.KeepUnsplashImageItem -> (holder as KeepUnsplashImageViewHolder).binding.executeAfter {
                lifecycleOwner = holder.itemView.findViewTreeLifecycleOwner()
                viewModel = imageKeepViewModel
                imageItem = uiModel.unsplashImageItem
                keyword = uiModel.unsplashImageItem.keyword
            }
            else -> return
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is KeepUnsplashImageItemUiModel.KeepUnsplashImageHeaderItem -> R.layout.item_keep_unsplash_image_header
            is KeepUnsplashImageItemUiModel.KeepUnsplashImageItem -> R.layout.item_keep_unsplash_image
            else -> throw IllegalStateException("Unknown viewType")
        }
    }
}