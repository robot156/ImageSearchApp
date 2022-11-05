package com.example.imagesearchapp.domain.usecase.unsplashimage.imagekeep

import androidx.paging.PagingData
import com.example.imagesearchapp.domain.di.IoDispatcher
import com.example.imagesearchapp.domain.usecase.FlowUseCase
import com.example.imagesearchapp.domain.usecase.unsplashimage.UnsplashImageRepository
import com.example.imagesearchapp.domain.usecase.unsplashimage.entity.UnsplashImageEntity
import com.example.imagesearchapp.domain.utils.ResultState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

open class GetKeepUnsplashImagesUseCase @Inject constructor(
    private val unsplashImageRepository: UnsplashImageRepository,
    @IoDispatcher ioDispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, PagingData<UnsplashImageEntity>>(ioDispatcher) {

    override fun execute(params: Unit): Flow<ResultState<PagingData<UnsplashImageEntity>>> {
        return unsplashImageRepository.getKeepUnsplashImages()
    }
}