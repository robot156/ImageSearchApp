package com.example.imagesearchapp.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.imagesearchapp.R

@BindingAdapter("onSingleClick")
fun bindOnSingleClick(view: View, onClickListener: View.OnClickListener) {
    view.setOnClickListener(object : OnSingleClickListener() {
        override fun onSingleClick(v: View) {
            onClickListener.onClick(v)
        }
    })
}

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        if (!isValidContextForGlide(view.context)) return

        Glide.with(view.context)
            .load(imageUrl)
            .thumbnail(0.3f)
            .placeholder(android.R.color.white)
            .error(R.drawable.ic_image_not)
            .into(view)
    }
}

@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean?) {
    if (isGone == null || isGone) {
        view.visibility = View.GONE
    } else {
        view.visibility = View.VISIBLE
    }
}

private fun isValidContextForGlide(context: Context): Boolean {
    if (context is Activity) {
        return !context.isFinishing
    }
    return true
}