package com.example.imagesearchapp.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.imagesearchapp.data.model.UnsplashPhoto
import com.example.imagesearchapp.service.ServiceApi
import retrofit2.HttpException
import java.io.IOException

private const val UNSPLASH_STARTING_PAGE_INDEX = 1

class UnsplashPagingSource(
    private val unsplashApi: ServiceApi,
    private val query: String
) : PagingSource<Int, UnsplashPhoto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhoto> {
        val position = params.key ?: UNSPLASH_STARTING_PAGE_INDEX
        return try {
            val response = unsplashApi.searchPhotos(query,position,params.loadSize)
            val photos = response.results

            LoadResult.Page(
                data = photos,
                prevKey = if(position == UNSPLASH_STARTING_PAGE_INDEX) null else position -1,
                nextKey = if(photos.isEmpty()) null else position + 1
            )
        } catch (exception : IOException) {
            LoadResult.Error(exception)
        } catch (exception : HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UnsplashPhoto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}