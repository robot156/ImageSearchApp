package com.example.imagesearchapp.screen.imagekeep

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import com.example.imagesearchapp.data.model.UnsplashPhotoItem
import com.example.imagesearchapp.domain.UnsplashLocalRepository
import com.example.imagesearchapp.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ImageKeepViewModel @Inject constructor(
    private val unsplashLocalRepository: UnsplashLocalRepository,
    imageKeepViewModelDelegate: ImageKeepViewModelDelegate
) : ViewModel(), ImageKeepViewModelDelegate by imageKeepViewModelDelegate {

    private val unsplashPhotoItemActionReceiver: StateFlow<UnsplashPhotoItemAction> = unsplashPhotoItemAction.stateIn(viewModelScope, SharingStarted.Eagerly, UnsplashPhotoItemAction.None)

    val keepImages = unsplashPhotoItemActionReceiver
        .flatMapLatest {
            unsplashLocalRepository.getUnSplashPhotos().map { pagingData ->
                pagingData.insertSeparators { before: UnsplashPhotoItem?, after: UnsplashPhotoItem? ->
                    setKeepImageEmpty(before == null && after == null)
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

    fun setKeepImageEmpty(isEmpty: Boolean) {
        _isKeepImageEmpty.value = isEmpty
    }

    fun navigateToDetail(photoItem: UnsplashPhotoItem) {
        _navigateToDetail.value = Event(photoItem)
    }
}