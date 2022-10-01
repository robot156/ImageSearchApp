package com.example.imagesearchapp.util

import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar

abstract class OnSingleClickListener(private val minClickInterval: Long = 500) : View.OnClickListener {

    private var enabled = true
    private val enableAgain = Runnable { enabled = true }

    override fun onClick(v: View) {
        if (enabled) {
            enabled = false
            v.postDelayed(enableAgain, minClickInterval)
            onSingleClick(v)
        }
    }

    abstract fun onSingleClick(v: View)
}

abstract class OnMenuItemSingleClickListener(private val toolbar: Toolbar, private val minClickInterval: Long = 500) : Toolbar.OnMenuItemClickListener {

    private var enabled = true
    private val enableAgain = Runnable { enabled = true }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        return if (enabled) {
            enabled = false
            toolbar.postDelayed(enableAgain, minClickInterval)
            onMenuSingleClick(item)
        } else {
            false
        }
    }

    abstract fun onMenuSingleClick(item: MenuItem): Boolean
}

fun View.setOnSingleClickListener(minClickInterval: Long = 500, onClick: () -> Unit) {
    this.setOnClickListener(object : OnSingleClickListener(minClickInterval) {
        override fun onSingleClick(v: View) {
            onClick()
        }
    })
}

fun Toolbar.setOnMenuItemSingleClickListener(minClickInterval: Long = 500, onMenuClick: (item: MenuItem) -> Boolean) {
    this.setOnMenuItemClickListener(object : OnMenuItemSingleClickListener(this@setOnMenuItemSingleClickListener, minClickInterval) {
        override fun onMenuSingleClick(item: MenuItem): Boolean {
            return onMenuClick(item)
        }
    })
}
