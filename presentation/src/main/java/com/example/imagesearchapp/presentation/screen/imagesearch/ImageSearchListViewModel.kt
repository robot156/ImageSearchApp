package com.example.imagesearchapp.presentation.screen.imagesearch

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.example.imagesearchapp.domain.usecase.unsplashimage.imagesearch.GetSearchUnsplashImagesUseCase
import com.example.imagesearchapp.domain.utils.data
import com.example.imagesearchapp.presentation.model.UnsplashImageItem
import com.example.imagesearchapp.presentation.model.mapToItem
import com.example.imagesearchapp.presentation.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ImageSearchListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getSearchUnsplashImagesUseCase: GetSearchUnsplashImagesUseCase
) : ViewModel() {

    private val clearListCh = Channel<Unit>(Channel.CONFLATED)

    val keyword = savedStateHandle.getLiveData<String>(KEY_KEYWORD).asFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val images: Flow<PagingData<UnsplashImageItem>> = merge(
        clearListCh.receiveAsFlow().map { PagingData.empty() },
        keyword.filterNotNull().flatMapLatest { keyword ->
            getSearchUnsplashImagesUseCase(GetSearchUnsplashImagesUseCase.Params(query = keyword))
                .map { result ->
                    result.data!!.map { data -> data.mapToItem() }
                }.map { pagingData ->
                    pagingData.insertSeparators { before: UnsplashImageItem?, after: UnsplashImageItem? ->
                        setListEmpty(before == null && after == null)
                        null
                    }
                }
                .distinctUntilChanged()
        }
    ).cachedIn(viewModelScope)

    private val _isSearchMenuVisible = MutableLiveData<Boolean>(false)
    val isSearchMenuVisible: LiveData<Boolean>
        get() = _isSearchMenuVisible

    private val _isSearchImageEmpty = MutableLiveData<Boolean>(false)
    val isSearchImageEmpty: LiveData<Boolean>
        get() = _isSearchImageEmpty

    private val _clickRefresh = MutableLiveData<Event<Unit>>()
    val clickRefresh: LiveData<Event<Unit>>
        get() = _clickRefresh

    private val _navigateToDetail = MutableLiveData<Event<UnsplashImageItem>>()
    val navigateToDetail: LiveData<Event<UnsplashImageItem>>
        get() = _navigateToDetail

    private val _navigateToBack = MutableLiveData<Event<Unit>>()
    val navigateToBack: LiveData<Event<Unit>>
        get() = _navigateToBack

    private fun setListEmpty(isEmpty: Boolean) {
        _isSearchImageEmpty.value = isEmpty
    }

    fun setKeyword(keyword: String) {
        if (this.keyword.value == keyword) {
            return
        }

        clearListCh.trySend(Unit)
        savedStateHandle.set(KEY_KEYWORD, keyword)
    }

    fun setSearchMenuVisible(isVisible: Boolean) {
        _isSearchMenuVisible.value = isVisible
    }

    fun clickRefresh() {
        _clickRefresh.value = Event(Unit)
    }

    fun navigateToDetail(imageItem: UnsplashImageItem) {
        _navigateToDetail.value = Event(imageItem)
    }

    fun navigateToBack() {
        _navigateToBack.value = Event(Unit)
    }

    companion object {
        private const val KEY_KEYWORD = "keyword"
    }
}