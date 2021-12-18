package com.example.imagesearchapp

import android.app.Application
import androidx.core.content.res.ResourcesCompat
import dagger.hilt.android.HiltAndroidApp
import es.dmoral.toasty.Toasty
import timber.log.Timber

@HiltAndroidApp
class ImageSearchApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initToasty()
        initTimber()
    }

    private fun initToasty() {
        Toasty.Config.getInstance()
            .setTextSize(14)
            .apply {
                ResourcesCompat.getFont(this@ImageSearchApp, R.font.font_demi_light)?.let {
                    setToastTypeface(it)
                }
            }
            .apply()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}