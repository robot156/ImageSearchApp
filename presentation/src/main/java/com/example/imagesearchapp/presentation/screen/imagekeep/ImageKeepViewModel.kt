package com.example.imagesearchapp.presentation.screen.imagekeep

import androidx.lifecycle.*
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.example.imagesearchapp.domain.usecase.unsplashimage.imagekeep.GetKeepUnsplashImagesUseCase
import com.example.imagesearchapp.domain.utils.data
import com.example.imagesearchapp.presentation.model.UnsplashImageItem
import com.example.imagesearchapp.presentation.model.mapToItem
import com.example.imagesearchapp.presentation.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class ImageKeepViewModel @Inject constructor(
    private val getKeepUnsplashImagesUseCase: GetKeepUnsplashImagesUseCase
) : ViewModel() {

    val keepImages = getKeepUnsplashImagesUseCase(Unit)
        .map { result -> result.data!!.map { it.mapToItem() } }
        .map { pagingData ->
            pagingData.insertSeparators { before: UnsplashImageItem?, after: UnsplashImageItem? ->
                setKeepImageEmpty(before == null && after == null)
                null
            }
        }.map { pagingData ->
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

    private val _isKeepImageEmpty = MutableLiveData<Boolean>(false)
    val isKeepImageEmpty: LiveData<Boolean>
        get() = _isKeepImageEmpty

    private val _navigateToDetail = MutableLiveData<Event<UnsplashImageItem>>()
    val navigateToDetail: LiveData<Event<UnsplashImageItem>>
        get() = _navigateToDetail

    private fun setKeepImageEmpty(isEmpty: Boolean) {
        _isKeepImageEmpty.value = isEmpty
    }

    fun navigateToDetail(imageItem: UnsplashImageItem) {
        _navigateToDetail.value = Event(imageItem)
    }
}

sealed class KeepUnsplashImageItemUiModel {
    data class KeepUnsplashImageHeaderItem(val keyword: String) : KeepUnsplashImageItemUiModel()
    data class KeepUnsplashImageItem(val unsplashImageItem: UnsplashImageItem) : KeepUnsplashImageItemUiModel()
}