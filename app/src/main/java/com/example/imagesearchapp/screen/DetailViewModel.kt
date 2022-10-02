package com.example.imagesearchapp.screen

import androidx.lifecycle.*
import com.example.imagesearchapp.data.model.UnsplashPhotoItem
import com.example.imagesearchapp.domain.FileSaveRepository
import com.example.imagesearchapp.domain.UnsplashLocalRepository
import com.example.imagesearchapp.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val fileSaveRepository: FileSaveRepository,
    private val unSplashLocalRepository: UnsplashLocalRepository
) : ViewModel() {

    val keyword = savedStateHandle.get<String>(KEY_KEYWORD)
    val unsplashPhoto = savedStateHandle.get<UnsplashPhotoItem>(KEY_PHOTO_DATA)

    private val _isKeepImage = MutableLiveData<Boolean>(false)
    val isKeepImage: LiveData<Boolean>
        get() = _isKeepImage

    private val _saveToAlbum = MutableLiveData<Event<Unit>>()
    val saveToAlbum: LiveData<Event<Unit>>
        get() = _saveToAlbum

    private val _saveComplete = MutableLiveData<Event<Unit>>()
    val saveComplete: LiveData<Event<Unit>>
        get() = _saveComplete

    private val _saveFail = MutableLiveData<Event<Unit>>()
    val saveFail: LiveData<Event<Unit>>
        get() = _saveFail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    init {
        viewModelScope.launch {
            launch {
                _isKeepImage.value = unsplashPhoto
                    ?.let {
                        unSplashLocalRepository.getUnSplash(imageId = it.id) != null
                    } ?: false
            }
        }
    }

    fun setImageKeep(unsplashPhoto: UnsplashPhotoItem?) {
        viewModelScope.launch {
            unsplashPhoto?.let {
                if (isKeepImage.value == false) {
                    unSplashLocalRepository.setUnSplash(it.copy(keyword = (keyword ?: "").trim()))
                } else {
                    unSplashLocalRepository.deleteUnSplashPhoto(it.id)
                }

                _isKeepImage.value = !(isKeepImage.value ?: true)
            }
        }
    }

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

                setLoading(false)
            }
        }
    }

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    companion object {
        private const val KEY_KEYWORD = "keyword"
        private const val KEY_PHOTO_DATA = "photo_data"
    }
}