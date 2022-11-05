package com.example.imagesearchapp.presentation.screen.imagesearch

import androidx.lifecycle.*
import com.example.imagesearchapp.presentation.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ImageSearchViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _keyword = savedStateHandle.getLiveData(KEY_KEYWORD, "")
    val keyword: LiveData<String>
        get() = _keyword

    private val _enabledInputKeyword = MediatorLiveData<Boolean>()
    val enabledInputKeyword: LiveData<Boolean>
        get() = _enabledInputKeyword

    private val _navigateToDetail = MutableLiveData<Event<String>>()
    val navigateToDetail: LiveData<Event<String>>
        get() = _navigateToDetail

    private val _navigateToStorage = MutableLiveData<Event<Unit>>()
    val navigateToStorage: LiveData<Event<Unit>>
        get() = _navigateToStorage

    init {
        with(_enabledInputKeyword) {
            addSource(keyword) {
                value = isValidEnterState()
            }
        }
    }

    private fun isValidEnterState() = !keyword.value.isNullOrEmpty() && !keyword.value.isNullOrBlank()

    fun setKeyword(keyword: String?) {
        if (this.keyword.value == keyword || keyword == null) {
            return
        }
        savedStateHandle.set(KEY_KEYWORD, keyword)
    }

    fun navigateToList(keyword: String?) {
        _navigateToDetail.value = Event(keyword!!)
    }

    fun navigateToStorage() {
        _navigateToStorage.value = Event(Unit)
    }

    companion object {
        private const val KEY_KEYWORD = "keyword"
    }
}