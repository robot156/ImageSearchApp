package com.example.imagesearchapp.util

import android.app.Activity
import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.imagesearchapp.R

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        if (!isValidContextForGlide(view.context)) return

        Glide.with(view.context)
            .load(imageUrl)
            .thumbnail(0.3f)
            .placeholder(R.color.white)
            .error(R.drawable.ic_image_not)
            .into(view)
    }
}

private fun isValidContextForGlide(context: Context): Boolean {
    if (context is Activity) {
        return !context.isFinishing
    }
    return true
}