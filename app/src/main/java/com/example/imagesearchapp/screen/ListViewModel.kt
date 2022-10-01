package com.example.imagesearchapp.screen

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
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
        .map { pagingData ->
            pagingData.map { it.mapToItem() }
        }
        .distinctUntilChanged()
        .cachedIn(viewModelScope)

    private val _clickRefresh = MutableLiveData<Event<Unit>>()
    val clickRefresh: LiveData<Event<Unit>>
        get() = _clickRefresh

    private val _navigateToDetail = MutableLiveData<Event<UnsplashPhotoItem>>()
    val navigateToDetail: LiveData<Event<UnsplashPhotoItem>>
        get() = _navigateToDetail

    fun clickRefresh() {
        _clickRefresh.value = Event(Unit)
    }

    fun navigateToDetail(photoData: UnsplashPhotoItem) {
        _navigateToDetail.value = Event(photoData)
    }

    companion object {
        private const val KEY_KEYWORD = "keyword"
    }
}