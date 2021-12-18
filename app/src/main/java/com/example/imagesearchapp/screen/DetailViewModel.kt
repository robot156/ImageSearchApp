package com.example.imagesearchapp.screen

import androidx.lifecycle.*
import com.example.imagesearchapp.data.model.UnsplashPhoto
import com.example.imagesearchapp.domain.FileSaveRepository
import com.example.imagesearchapp.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val fileSaveRepository: FileSaveRepository
) : ViewModel() {

    val keyword = savedStateHandle.get<String>(KEY_KEYWORD)
    val unsplashPhoto = savedStateHandle.get<UnsplashPhoto>(KEY_PHOTO_DATA)

    private val _saveToAlbum = MutableLiveData<Event<Unit>>()
    val saveToAlbum: LiveData<Event<Unit>>
        get() = _saveToAlbum

    private val _saveComplete = MutableLiveData<Event<Unit>>()
    val saveComplete: LiveData<Event<Unit>>
        get() = _saveComplete

    private val _saveFail = MutableLiveData<Event<Unit>>()
    val saveFail: LiveData<Event<Unit>>
        get() = _saveFail

    fun saveToAlbum() {
        _saveToAlbum.value = Event(Unit)
    }

    fun imageSave(byteArray: ByteArray, imagePath: String? = null, fileName: String) {
        viewModelScope.launch {
            fileSaveRepository.saveImageFileByByteArray(
                byteArray = byteArray,
                filePath = imagePath,
                fileName = fileName
            ).let {
                if (it.isNullOrEmpty()) {
                    _saveFail.value = Event(Unit)
                } else {
                    _saveComplete.value = Event(Unit)
                }
            }
        }
    }

    companion object {
        private const val KEY_KEYWORD = "keyword"
        private const val KEY_PHOTO_DATA = "photo_data"
    }
}