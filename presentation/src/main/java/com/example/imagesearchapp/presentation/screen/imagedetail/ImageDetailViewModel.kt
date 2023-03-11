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
import com.example.imagesearchapp.presentation.utils.Const.EVENT_EXTRA_BUFFER
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
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

    private val _isKeepImage = MutableStateFlow<Boolean>(true)
    val isKeepImage: StateFlow<Boolean> = _isKeepImage

    private val _saveToAlbum = MutableSharedFlow<Unit>(0, EVENT_EXTRA_BUFFER, BufferOverflow.DROP_LATEST)
    val saveToAlbum: SharedFlow<Unit> = _saveToAlbum

    private val _saveComplete = MutableSharedFlow<Unit>(0, EVENT_EXTRA_BUFFER, BufferOverflow.DROP_LATEST)
    val saveComplete: SharedFlow<Unit> = _saveComplete

    private val _saveFail = MutableSharedFlow<Unit>(0, EVENT_EXTRA_BUFFER, BufferOverflow.DROP_LATEST)
    val saveFail: SharedFlow<Unit> = _saveFail

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
                if (!isKeepImage.value) {
                    saveKeepUnsplashImageUseCase(
                        SaveKeepUnsplashImageUseCase.Params(
                            it.copy(keyword = (keyword ?: "").trim()).mapToEntity()
                        )
                    )
                } else {
                    deleteKeepUnsplashImageUseCase(DeleteKeepUnsplashImageUseCase.Params(it.id))
                }

                _isKeepImage.value = !isKeepImage.value
            }
        }
    }

    fun saveToAlbum() {
        viewModelScope.launch {
            _saveToAlbum.emit(Unit)
        }
    }

    fun imageSave(byteArray: ByteArray, imagePath: String? = null, fileName: String) {
        viewModelScope.launch {
            saveImageFileUseCase(
                SaveImageFileUseCase.Params(
                    byteArray = byteArray,
                    filePath = imagePath,
                    fileName = fileName
                )
            ).collect {
                when (it) {
                    is ResultState.Loading -> return@collect
                    is ResultState.Success -> _saveComplete.emit(Unit)
                    is ResultState.Error -> _saveFail.emit(Unit)
                }
            }
        }
    }

    companion object {
        private const val KEY_KEYWORD = "keyword"
        private const val KEY_IMAGE_DATA = "image_data"
    }
}