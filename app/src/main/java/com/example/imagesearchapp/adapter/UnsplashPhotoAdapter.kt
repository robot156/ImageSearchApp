package com.example.imagesearchapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.imagesearchapp.R
import com.example.imagesearchapp.adapter.viewholder.UnsplashPhotoViewHolder
import com.example.imagesearchapp.data.model.UnsplashPhotoItem
import com.example.imagesearchapp.databinding.CellUnsplashPhotoBinding
import com.example.imagesearchapp.screen.ListViewModel
import com.example.imagesearchapp.util.executeAfter

class UnsplashPhotoAdapter(
    private val listViewModel: ListViewModel
) : PagingDataAdapter<UnsplashPhotoItem, UnsplashPhotoViewHolder>(PHOTO_COMPARATOR) {

    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<UnsplashPhotoItem>() {
            override fun areItemsTheSame(oldItem: UnsplashPhotoItem, newItem: UnsplashPhotoItem) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: UnsplashPhotoItem, newItem: UnsplashPhotoItem) =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnsplashPhotoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return UnsplashPhotoViewHolder(CellUnsplashPhotoBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holderUnSplash: UnsplashPhotoViewHolder, position: Int) {
        getItem(position)?.let {
            holderUnSplash.binding.executeAfter {
                viewModel = listViewModel
                lifecycleOwner = holderUnSplash.itemView.findViewTreeLifecycleOwner()
                photo = it
            }
        }
    }

    override fun getItemViewType(position: Int) = R.layout.cell_unsplash_photo
}