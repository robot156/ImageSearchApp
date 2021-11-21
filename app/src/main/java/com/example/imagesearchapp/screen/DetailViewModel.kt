package com.example.imagesearchapp.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.imagesearchapp.data.model.UnsplashPhoto
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val keyword = savedStateHandle.get<String>(KEY_KEYWORD)
    val unsplashPhoto = savedStateHandle.get<UnsplashPhoto>(KEY_PHOTO_DATA)

    companion object {
        private const val KEY_KEYWORD = "keyword"
        private const val KEY_PHOTO_DATA = "photo_data"
    }
}