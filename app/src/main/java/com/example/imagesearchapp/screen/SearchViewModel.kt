package com.example.imagesearchapp.screen

import androidx.lifecycle.*
import com.example.imagesearchapp.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
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

    init {
        with(_enabledInputKeyword) {
            addSource(keyword) {
                value = isValidEnterState()
            }
        }
    }

    private fun isValidEnterState() = !keyword.value.isNullOrEmpty()

    fun setKeyword(keyword: String?) {
        if (this.keyword.value == keyword || keyword == null) {
            return
        }
        savedStateHandle.set(KEY_KEYWORD, keyword)
    }

    fun navigateToList(keyword: String?) { // 리스트 화면으로 이동
        _navigateToDetail.value = Event(keyword!!)
    }

    companion object {
        private const val KEY_KEYWORD = "keyword"
    }
}