package com.example.imagesearchapp.screen

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.imagesearchapp.data.model.UnsplashPhoto
import com.example.imagesearchapp.domain.UnSplashRepository
import com.example.imagesearchapp.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: UnSplashRepository
) : ViewModel() {

    val keyword = savedStateHandle.get<String>(KEY_KEYWORD) ?: ""

    val photos: Flow<PagingData<UnsplashPhoto>> = repository.getSearchResults(keyword).distinctUntilChanged().cachedIn(viewModelScope)

    private val _clickRefresh = MutableLiveData<Event<Unit>>()
    val clickRefresh: LiveData<Event<Unit>>
        get() = _clickRefresh

    private val _navigateToDetail = MutableLiveData<Event<UnsplashPhoto>>()
    val navigateToDetail : LiveData<Event<UnsplashPhoto>>
        get() = _navigateToDetail

    fun clickRefresh() {
        _clickRefresh.value = Event(Unit)
    }

    fun navigateToDetail(photoData: UnsplashPhoto) {
        _navigateToDetail.value = Event(photoData)
    }

    companion object {
        private const val KEY_KEYWORD = "keyword"
    }
}