package com.example.imagesearchapp.screen.imagekeep

import com.example.imagesearchapp.data.model.UnsplashPhotoItem
import com.example.imagesearchapp.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface ImageKeepViewModelDelegate {

    val unsplashPhotoItemAction: SharedFlow<UnsplashPhotoItemAction>

    fun addUnsplashPhoto(unsplashPhotoItem: UnsplashPhotoItem)

    fun deleteUnsplashPhoto(unsplashPhotoItem: UnsplashPhotoItem)
}

sealed class UnsplashPhotoItemAction {
    object None : UnsplashPhotoItemAction()
    data class AddUnsplashPhotoItem(val unsplashPhotoItem: UnsplashPhotoItem) : UnsplashPhotoItemAction()
    data class DeleteUnsplashPhotoItem(val unsplashPhotoItem: UnsplashPhotoItem) : UnsplashPhotoItemAction()
}

class ImageKeepViewModelDelegateImpl @Inject constructor(
    @ApplicationScope val applicationScope: CoroutineScope
) : ImageKeepViewModelDelegate {

    private val _unsplashPhotoItemAction: MutableSharedFlow<UnsplashPhotoItemAction> = MutableSharedFlow(
        extraBufferCapacity = Int.MAX_VALUE,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    override val unsplashPhotoItemAction: SharedFlow<UnsplashPhotoItemAction> = _unsplashPhotoItemAction

    override fun addUnsplashPhoto(unsplashPhotoItem: UnsplashPhotoItem) {
        applicationScope.launch {
            _unsplashPhotoItemAction.emit(UnsplashPhotoItemAction.AddUnsplashPhotoItem(unsplashPhotoItem))
        }
    }

    override fun deleteUnsplashPhoto(unsplashPhotoItem: UnsplashPhotoItem) {
        applicationScope.launch {
            _unsplashPhotoItemAction.emit(UnsplashPhotoItemAction.DeleteUnsplashPhotoItem(unsplashPhotoItem))
        }
    }
}