package com.example.imagesearchapp.domain.usecase.unsplashimage.imagekeep

import com.example.imagesearchapp.domain.di.IoDispatcher
import com.example.imagesearchapp.domain.usecase.UseCase
import com.example.imagesearchapp.domain.usecase.unsplashimage.UnsplashImageRepository
import com.example.imagesearchapp.domain.usecase.unsplashimage.entity.UnsplashImageEntity
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

open class GetKeepUnsplashImageUseCase @Inject constructor(
    private val unsplashImageRepository: UnsplashImageRepository,
    @IoDispatcher ioDispatcher: CoroutineDispatcher
) : UseCase<GetKeepUnsplashImageUseCase.Params, UnsplashImageEntity?>(ioDispatcher) {

    override suspend fun execute(params: Params): UnsplashImageEntity? {
        return unsplashImageRepository.getKeepUnsplashImage(params.imageId)
    }

    data class Params(
        val imageId: String
    )
}