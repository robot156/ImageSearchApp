package com.example.imagesearchapp.presentation.screen.imagedetail

import androidx.lifecycle.*
import com.example.imagesearchapp.domain.usecase.file.SaveImageFileUseCase
import com.example.imagesearchapp.domain.usecase.unsplashimage.imagekeep.DeleteKeepUnsplashImageUseCase
import com.example.imagesearchapp.domain.usecase.unsplashimage.imagekeep.GetKeepUnsplashImageUseCase
import com.example.imagesearchapp.domain.usecase.unsplashimage.imagekeep.SaveKeepUnsplashImageUseCase
import com.example.imagesearchapp.domain.utils.ResultState
import com.example.imagesearchapp.domain.utils.data
import com.example.imagesearchapp.presentation.model.UnsplashImageItem
import com.example.imagesearchapp.presentation.model.mapToEntity
import com.example.imagesearchapp.presentation.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val saveImageFileUseCase: SaveImageFileUseCase,
    private val getKeepUnsplashImageUseCase: GetKeepUnsplashImageUseCase,
    private val saveKeepUnsplashImageUseCase: SaveKeepUnsplashImageUseCase,
    private val deleteKeepUnsplashImageUseCase: DeleteKeepUnsplashImageUseCase
) : ViewModel() {

    val keyword = savedStateHandle.get<String>(KEY_KEYWORD)
    val unsplashImage = savedStateHandle.get<UnsplashImageItem>(KEY_IMAGE_DATA)

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
            _isKeepImage.value = unsplashImage
                ?.let {
                    getKeepUnsplashImageUseCase(GetKeepUnsplashImageUseCase.Params(it.id)).data != null
                } ?: false
        }
    }

    fun setImageKeep(unsplashImageItem: UnsplashImageItem?) {
        viewModelScope.launch {
            unsplashImageItem?.let {
                if (isKeepImage.value == false) {
                    saveKeepUnsplashImageUseCase(
                        SaveKeepUnsplashImageUseCase.Params(
                            it.copy(keyword = (keyword ?: "").trim()).mapToEntity()
                        )
                    )
                } else {
                    deleteKeepUnsplashImageUseCase(DeleteKeepUnsplashImageUseCase.Params(it.id))
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
            saveImageFileUseCase(
                SaveImageFileUseCase.Params(
                    byteArray = byteArray,
                    filePath = imagePath,
                    fileName = fileName
                )
            ).onEach {
                setLoading(it is ResultState.Loading)
            }.collect {
                when (it) {
                    is ResultState.Loading -> return@collect
                    is ResultState.Success -> _saveComplete.value = Event(Unit)
                    is ResultState.Error -> _saveFail.value = Event(Unit)
                }
            }
        }
    }

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    companion object {
        private const val KEY_KEYWORD = "keyword"
        private const val KEY_IMAGE_DATA = "image_data"
    }
}