package com.example.imagesearchapp.screen

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.example.imagesearchapp.data.model.UnsplashPhoto
import com.example.imagesearchapp.data.model.UnsplashPhotoItem
import com.example.imagesearchapp.data.model.mapToItem
import com.example.imagesearchapp.domain.UnsplashRemoteRepository
import com.example.imagesearchapp.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    repository: UnsplashRemoteRepository
) : ViewModel() {

    val keyword = savedStateHandle.get<String>(KEY_KEYWORD) ?: ""

    val photos: Flow<PagingData<UnsplashPhotoItem>> = repository
        .getSearchResults(keyword)
        .map { pagingData -> pagingData.map { it.mapToItem() } }
        .map { pagingData ->
            pagingData.insertSeparators { before: UnsplashPhotoItem?, after: UnsplashPhotoItem? ->
                setListEmpty(before == null && after == null)
                null
            }
        }
        .distinctUntilChanged()
        .cachedIn(viewModelScope)

    private val _isSearchImageEmpty = MutableLiveData<Boolean>(false)
    val isSearchImageEmpty: LiveData<Boolean>
        get() = _isSearchImageEmpty

    private val _clickRefresh = MutableLiveData<Event<Unit>>()
    val clickRefresh: LiveData<Event<Unit>>
        get() = _clickRefresh

    private val _navigateToDetail = MutableLiveData<Event<UnsplashPhotoItem>>()
    val navigateToDetail: LiveData<Event<UnsplashPhotoItem>>
        get() = _navigateToDetail

    private val _navigateToBack = MutableLiveData<Event<Unit>>()
    val navigateToBack: LiveData<Event<Unit>>
        get() = _navigateToBack

    private fun setListEmpty(isEmpty: Boolean) {
        _isSearchImageEmpty.value = isEmpty
    }

    fun clickRefresh() {
        _clickRefresh.value = Event(Unit)
    }

    fun navigateToDetail(photoData: UnsplashPhotoItem) {
        _navigateToDetail.value = Event(photoData)
    }

    fun navigateToBack() {
        _navigateToBack.value = Event(Unit)
    }

    companion object {
        private const val KEY_KEYWORD = "keyword"
    }
}