package com.example.imagesearchapp.presentation.screen.imagekeep

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.example.imagesearchapp.domain.usecase.unsplashimage.imagekeep.GetKeepUnsplashImagesUseCase
import com.example.imagesearchapp.domain.utils.data
import com.example.imagesearchapp.presentation.model.UnsplashImageItem
import com.example.imagesearchapp.presentation.model.mapToItem
import com.example.imagesearchapp.presentation.utils.Const.EVENT_EXTRA_BUFFER
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageKeepViewModel @Inject constructor(
    getKeepUnsplashImagesUseCase: GetKeepUnsplashImagesUseCase
) : ViewModel() {

    val keepImages = getKeepUnsplashImagesUseCase(Unit)
        .map { result -> result.data!!.map { it.mapToItem() } }
        .map { pagingData ->
            pagingData.map { KeepUnsplashImageItemUiModel.KeepUnsplashImageItem(it) }
        }.map { pagingData ->
            pagingData.insertSeparators { before: KeepUnsplashImageItemUiModel.KeepUnsplashImageItem?, after: KeepUnsplashImageItemUiModel.KeepUnsplashImageItem? ->
                if (before?.unsplashImageItem?.keyword != after?.unsplashImageItem?.keyword && after?.unsplashImageItem?.keyword != null) {
                    KeepUnsplashImageItemUiModel.KeepUnsplashImageHeaderItem(after.unsplashImageItem.keyword)
                } else {
                    null
                }
            }
        }.cachedIn(viewModelScope)

    private val _navigateToDetail = MutableSharedFlow<UnsplashImageItem>(0, EVENT_EXTRA_BUFFER, BufferOverflow.DROP_LATEST)
    val navigateToDetail: SharedFlow<UnsplashImageItem> = _navigateToDetail

    fun navigateToDetail(imageItem: UnsplashImageItem) {
        viewModelScope.launch {
            _navigateToDetail.emit(imageItem)
        }
    }
}

sealed class KeepUnsplashImageItemUiModel {
    data class KeepUnsplashImageHeaderItem(val keyword: String) : KeepUnsplashImageItemUiModel()
    data class KeepUnsplashImageItem(val unsplashImageItem: UnsplashImageItem) : KeepUnsplashImageItemUiModel()
}