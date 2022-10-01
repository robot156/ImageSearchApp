package com.example.imagesearchapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.imagesearchapp.R
import com.example.imagesearchapp.adapter.viewholder.UnsplashKeepPhotoViewHolder
import com.example.imagesearchapp.data.model.UnsplashPhotoItem
import com.example.imagesearchapp.databinding.CellUnsplashKeepPhotoBinding
import com.example.imagesearchapp.screen.imagekeep.ImageKeepViewModel

class UnsplashKeepPhotoAdapter(
    private val imageKeepViewModel: ImageKeepViewModel
) : PagingDataAdapter<UnsplashPhotoItem, UnsplashKeepPhotoViewHolder>(PHOTO_COMPARATOR) {

    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<UnsplashPhotoItem>() {
            override fun areItemsTheSame(oldItem: UnsplashPhotoItem, newItem: UnsplashPhotoItem) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: UnsplashPhotoItem, newItem: UnsplashPhotoItem) =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnsplashKeepPhotoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return UnsplashKeepPhotoViewHolder(CellUnsplashKeepPhotoBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: UnsplashKeepPhotoViewHolder, position: Int) {
        holder.binding.apply {
            viewModel = imageKeepViewModel
            lifecycleOwner = holder.itemView.findViewTreeLifecycleOwner()
            photo = getItem(position)
            executePendingBindings()
        }
    }

    override fun getItemViewType(position: Int) = R.layout.cell_unsplash_keep_photo

}