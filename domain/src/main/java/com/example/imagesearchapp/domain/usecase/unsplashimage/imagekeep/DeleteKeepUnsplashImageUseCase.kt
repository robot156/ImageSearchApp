package com.example.imagesearchapp.domain.usecase.unsplashimage.imagekeep

import com.example.imagesearchapp.domain.di.IoDispatcher
import com.example.imagesearchapp.domain.usecase.UseCase
import com.example.imagesearchapp.domain.usecase.unsplashimage.UnsplashImageRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

open class DeleteKeepUnsplashImageUseCase @Inject constructor(
    private val unsplashImageRepository: UnsplashImageRepository,
    @IoDispatcher ioDispatcher: CoroutineDispatcher
) : UseCase<DeleteKeepUnsplashImageUseCase.Params, Unit>(ioDispatcher) {

    override suspend fun execute(params: Params) {
        unsplashImageRepository.deleteKeepUnsplashImage(params.imageId)
    }

    data class Params(
        val imageId: String
    )
}