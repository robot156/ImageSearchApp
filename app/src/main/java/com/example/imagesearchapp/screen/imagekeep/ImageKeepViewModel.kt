package com.example.imagesearchapp.screen.imagekeep

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.example.imagesearchapp.data.model.UnsplashPhotoItem
import com.example.imagesearchapp.domain.UnsplashLocalRepository
import com.example.imagesearchapp.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class ImageKeepViewModel @Inject constructor(
    unsplashLocalRepository: UnsplashLocalRepository
) : ViewModel() {

    val keepImages = unsplashLocalRepository.getUnSplashPhotos()
        .map { pagingData ->
            pagingData.insertSeparators { before: UnsplashPhotoItem?, after: UnsplashPhotoItem? ->
                setKeepImageEmpty(before == null && after == null)
                null
            }
        }.map { pagingData ->
            pagingData.map { KeepUnsplashPhotoItemUiModel.KeepUnsplashPhotoItem(it) }
        }.map { pagingData ->
            pagingData.insertSeparators { before: KeepUnsplashPhotoItemUiModel.KeepUnsplashPhotoItem?, after: KeepUnsplashPhotoItemUiModel.KeepUnsplashPhotoItem? ->
                if (before?.unsplashPhotoItem?.keyword != after?.unsplashPhotoItem?.keyword) {
                    KeepUnsplashPhotoItemUiModel.KeepUnsplashPhotoHeaderItem(after?.unsplashPhotoItem?.keyword ?: "")
                } else {
                    null
                }
            }
        }.cachedIn(viewModelScope)

    private val _isKeepImageEmpty = MutableLiveData<Boolean>(false)
    val isKeepImageEmpty: LiveData<Boolean>
        get() = _isKeepImageEmpty

    private val _navigateToDetail = MutableLiveData<Event<UnsplashPhotoItem>>()
    val navigateToDetail: LiveData<Event<UnsplashPhotoItem>>
        get() = _navigateToDetail

    private fun setKeepImageEmpty(isEmpty: Boolean) {
        _isKeepImageEmpty.value = isEmpty
    }

    fun navigateToDetail(photoItem: UnsplashPhotoItem) {
        _navigateToDetail.value = Event(photoItem)
    }
}

sealed class KeepUnsplashPhotoItemUiModel {
    data class KeepUnsplashPhotoHeaderItem(val keyword: String) : KeepUnsplashPhotoItemUiModel()
    data class KeepUnsplashPhotoItem(val unsplashPhotoItem: UnsplashPhotoItem) : KeepUnsplashPhotoItemUiModel()
}