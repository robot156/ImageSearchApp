package com.example.imagesearchapp.domain.usecase.unsplashimage.imagesearch

import androidx.paging.*
import com.example.imagesearchapp.domain.di.IoDispatcher
import com.example.imagesearchapp.domain.usecase.FlowUseCase
import com.example.imagesearchapp.domain.usecase.unsplashimage.UnsplashImageRepository
import com.example.imagesearchapp.domain.usecase.unsplashimage.entity.UnsplashImageEntity
import com.example.imagesearchapp.domain.utils.ResultState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

open class GetSearchUnsplashImagesUseCase @Inject constructor(
    private val unsplashImageRepository: UnsplashImageRepository,
    @IoDispatcher ioDispatcher: CoroutineDispatcher
) : FlowUseCase<GetSearchUnsplashImagesUseCase.Params, PagingData<UnsplashImageEntity>>(ioDispatcher) {

    override fun execute(params: Params): Flow<ResultState<PagingData<UnsplashImageEntity>>> = Pager(
        config = PagingConfig(pageSize = SEARCH_PAGE_SIZE, enablePlaceholders = true)
    ) {
        object : PagingSource<Int, UnsplashImageEntity>() {
            override fun getRefreshKey(state: PagingState<Int, UnsplashImageEntity>): Int? = null

            override suspend fun load(loadParams: LoadParams<Int>): LoadResult<Int, UnsplashImageEntity> {
                val page = loadParams.key ?: 1
                return try {
                    unsplashImageRepository.getSearchUnsplashImages(
                        query = params.query,
                        page = page,
                        limit = params.limit ?: SEARCH_PAGE_SIZE
                    ).let {
                        LoadResult.Page(
                            data = it,
                            prevKey = null,
                            nextKey = if (it.size == (params.limit ?: SEARCH_PAGE_SIZE)) page.plus(1) else null
                        )
                    }
                } catch (exception :Exception) {
                    println("GetSearchUnsplashImagesUseCase $exception")
                    LoadResult.Error(exception)
                }
            }
        }
    }.flow.map { ResultState.Success(it) }

    data class Params(
        val query: String,
        val limit: Int? = null
    )

    companion object {
        private const val SEARCH_PAGE_SIZE = 20
    }
}