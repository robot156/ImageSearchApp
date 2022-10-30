package com.example.imagesearchapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesearchapp.R
import com.example.imagesearchapp.adapter.viewholder.UnsplashKeepPhotoHeaderViewHolder
import com.example.imagesearchapp.adapter.viewholder.UnsplashKeepPhotoViewHolder
import com.example.imagesearchapp.databinding.CellUnsplashKeepPhotoBinding
import com.example.imagesearchapp.databinding.CellUnsplashKeepPhotoHeaderBinding
import com.example.imagesearchapp.screen.imagekeep.ImageKeepViewModel
import com.example.imagesearchapp.screen.imagekeep.KeepUnsplashPhotoItemUiModel
import com.example.imagesearchapp.util.executeAfter

class UnsplashKeepPhotoAdapter(
    private val imageKeepViewModel: ImageKeepViewModel
) : PagingDataAdapter<KeepUnsplashPhotoItemUiModel, RecyclerView.ViewHolder>(PHOTO_COMPARATOR) {

    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<KeepUnsplashPhotoItemUiModel>() {
            override fun areItemsTheSame(oldItem: KeepUnsplashPhotoItemUiModel, newItem: KeepUnsplashPhotoItemUiModel): Boolean {
                return (oldItem is KeepUnsplashPhotoItemUiModel.KeepUnsplashPhotoHeaderItem && newItem is KeepUnsplashPhotoItemUiModel.KeepUnsplashPhotoHeaderItem && oldItem.keyword == newItem.keyword) ||
                        (oldItem is KeepUnsplashPhotoItemUiModel.KeepUnsplashPhotoItem && newItem is KeepUnsplashPhotoItemUiModel.KeepUnsplashPhotoItem && oldItem.unsplashPhotoItem.id == newItem.unsplashPhotoItem.id)
            }

            override fun areContentsTheSame(oldItem: KeepUnsplashPhotoItemUiModel, newItem: KeepUnsplashPhotoItemUiModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.cell_unsplash_keep_photo_header -> UnsplashKeepPhotoHeaderViewHolder(CellUnsplashKeepPhotoHeaderBinding.inflate(inflater, parent, false))
            R.layout.cell_unsplash_keep_photo -> UnsplashKeepPhotoViewHolder(CellUnsplashKeepPhotoBinding.inflate(inflater, parent, false))
            else -> throw IllegalStateException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val uiModel = getItem(position)) {
            is KeepUnsplashPhotoItemUiModel.KeepUnsplashPhotoHeaderItem -> (holder as UnsplashKeepPhotoHeaderViewHolder).binding.executeAfter {
                lifecycleOwner = holder.itemView.findViewTreeLifecycleOwner()
                viewModel = imageKeepViewModel
                keyword = uiModel.keyword
            }
            is KeepUnsplashPhotoItemUiModel.KeepUnsplashPhotoItem -> (holder as UnsplashKeepPhotoViewHolder).binding.executeAfter {
                lifecycleOwner = holder.itemView.findViewTreeLifecycleOwner()
                viewModel = imageKeepViewModel
                photo = uiModel.unsplashPhotoItem
                keyword = uiModel.unsplashPhotoItem.keyword
            }
            else -> return
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is KeepUnsplashPhotoItemUiModel.KeepUnsplashPhotoHeaderItem -> R.layout.cell_unsplash_keep_photo_header
            is KeepUnsplashPhotoItemUiModel.KeepUnsplashPhotoItem -> R.layout.cell_unsplash_keep_photo
            else -> throw IllegalStateException("Unknown viewType")
        }
    }
}