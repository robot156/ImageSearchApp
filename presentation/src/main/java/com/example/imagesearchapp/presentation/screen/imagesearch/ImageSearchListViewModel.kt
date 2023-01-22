package com.example.imagesearchapp.presentation.screen.imagesearch

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.imagesearchapp.domain.usecase.unsplashimage.imagesearch.GetSearchUnsplashImagesUseCase
import com.example.imagesearchapp.domain.utils.data
import com.example.imagesearchapp.presentation.model.UnsplashImageItem
import com.example.imagesearchapp.presentation.model.mapToItem
import com.example.imagesearchapp.presentation.utils.Const.EVENT_EXTRA_BUFFER
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageSearchListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getSearchUnsplashImagesUseCase: GetSearchUnsplashImagesUseCase
) : ViewModel() {

    private val clearListCh = Channel<Unit>(Channel.CONFLATED)

    val keyword = savedStateHandle.getStateFlow<String?>(KEY_KEYWORD, null)

    val images: Flow<PagingData<UnsplashImageItem>> = merge(
        clearListCh.receiveAsFlow().map { PagingData.empty() },
        keyword.filterNotNull().flatMapLatest { keyword ->
            getSearchUnsplashImagesUseCase(GetSearchUnsplashImagesUseCase.Params(query = keyword))
                .map { result ->
                    result.data!!.map { data -> data.mapToItem() }
                }
        }
    ).cachedIn(viewModelScope)

    private val _isSearchMenuVisible = MutableStateFlow<Boolean>(false)
    val isSearchMenuVisible: StateFlow<Boolean> = _isSearchMenuVisible

    private val _clickRefresh = MutableSharedFlow<Unit>(0, EVENT_EXTRA_BUFFER, BufferOverflow.DROP_LATEST)
    val clickRefresh: SharedFlow<Unit> = _clickRefresh

    private val _navigateToDetail = MutableSharedFlow<UnsplashImageItem>(0, EVENT_EXTRA_BUFFER, BufferOverflow.DROP_LATEST)
    val navigateToDetail: SharedFlow<UnsplashImageItem> = _navigateToDetail

    private val _navigateToBack = MutableSharedFlow<Unit>(0, EVENT_EXTRA_BUFFER, BufferOverflow.DROP_LATEST)
    val navigateToBack: SharedFlow<Unit> = _navigateToBack

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
        viewModelScope.launch {
            _clickRefresh.emit(Unit)
        }
    }

    fun navigateToDetail(imageItem: UnsplashImageItem) {
        viewModelScope.launch {
            _navigateToDetail.emit(imageItem)
        }
    }

    fun navigateToBack() {
        viewModelScope.launch {
            _navigateToBack.emit(Unit)
        }
    }

    companion object {
        private const val KEY_KEYWORD = "keyword"
    }
}