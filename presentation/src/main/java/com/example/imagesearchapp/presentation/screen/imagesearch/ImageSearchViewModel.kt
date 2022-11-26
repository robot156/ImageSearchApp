package com.example.imagesearchapp.presentation.screen.imagesearch

import androidx.lifecycle.*
import com.example.imagesearchapp.presentation.utils.Const.EVENT_EXTRA_BUFFER
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageSearchViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val keyword = savedStateHandle.getStateFlow<String?>(KEY_KEYWORD, null)

    private val _navigateToSearchList = MutableSharedFlow<String>(0, EVENT_EXTRA_BUFFER, BufferOverflow.DROP_LATEST)
    val navigateToSearchList: SharedFlow<String> = _navigateToSearchList

    private val _navigateToKeep = MutableSharedFlow<Unit>(0, EVENT_EXTRA_BUFFER, BufferOverflow.DROP_LATEST)
    val navigateToKeep: SharedFlow<Unit> = _navigateToKeep

    fun setKeyword(keyword: String?) {
        if (this.keyword.value == keyword) {
            return
        }
        savedStateHandle.set(KEY_KEYWORD, keyword)
    }

    fun navigateToSearchList(keyword: String?) {
        viewModelScope.launch {
            _navigateToSearchList.emit(keyword!!)
        }
    }

    fun navigateToKeep() {
        viewModelScope.launch {
            _navigateToKeep.emit(Unit)
        }
    }

    companion object {
        private const val KEY_KEYWORD = "keyword"
    }
}