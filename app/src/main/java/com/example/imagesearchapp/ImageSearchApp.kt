package com.example.imagesearchapp

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import javax.inject.Provider

@HiltAndroidApp
class ImageSearchApp : Application(), ImageLoaderFactory {

    @Inject
    lateinit var imageLoader: Provider<ImageLoader>

    override fun newImageLoader(): ImageLoader = imageLoader.get()
}