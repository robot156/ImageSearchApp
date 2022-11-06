package com.example.imagesearchapp.domain.usecase.unsplashimage.imagekeep

import com.example.imagesearchapp.domain.di.IoDispatcher
import com.example.imagesearchapp.domain.usecase.UseCase
import com.example.imagesearchapp.domain.usecase.unsplashimage.UnsplashImageRepository
import com.example.imagesearchapp.domain.usecase.unsplashimage.entity.UnsplashImageEntity
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

open class SaveKeepUnsplashImageUseCase @Inject constructor(
    private val unsplashImageRepository: UnsplashImageRepository,
    @IoDispatcher ioDispatcher: CoroutineDispatcher
) : UseCase<SaveKeepUnsplashImageUseCase.Params, Unit>(ioDispatcher) {

    override suspend fun execute(params: Params) {
        unsplashImageRepository.setKeepUnsplashImage(params.unsplashImageEntity)
    }

    data class Params(
        val unsplashImageEntity: UnsplashImageEntity
    )
}