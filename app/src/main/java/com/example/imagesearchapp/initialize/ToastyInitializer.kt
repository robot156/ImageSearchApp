package com.example.imagesearchapp.initialize

import android.content.Context
import androidx.core.content.res.ResourcesCompat
import androidx.startup.Initializer
import com.example.imagesearchapp.R
import es.dmoral.toasty.Toasty

class ToastyInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        Toasty.Config.getInstance()
            .setTextSize(14)
            .apply {
                ResourcesCompat.getFont(context, R.font.font_demi_light)?.let {
                    setToastTypeface(it)
                }
            }
            .apply()
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}