package com.example.imagesearchapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.imagesearchapp.data.model.UnsplashPhoto
import com.example.imagesearchapp.databinding.CellUnsplashPhotoBinding
import com.example.imagesearchapp.screen.ListViewModel

class UnSplashPhotoAdapter(
    private val adapterLifecycleOwner: LifecycleOwner,
    private val listViewModel: ListViewModel
) : PagingDataAdapter<UnsplashPhoto, UnSplashPhotoViewHolder>(PHOTO_COMPARATOR) {

    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<UnsplashPhoto>() {
            override fun areItemsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto) =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnSplashPhotoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return UnSplashPhotoViewHolder(CellUnsplashPhotoBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holderUnSplash: UnSplashPhotoViewHolder, position: Int) {
        getItem(position)?.let {
            holderUnSplash.binding.apply {
                photoData = it
                lifecycleOwner = adapterLifecycleOwner
                viewModel = listViewModel
                executePendingBindings()
            }
        }
    }
}