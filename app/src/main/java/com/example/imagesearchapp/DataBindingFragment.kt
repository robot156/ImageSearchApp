package com.example.imagesearchapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class DataBindingFragment<T : ViewDataBinding>(@LayoutRes private val layoutResId: Int) : Fragment() {

    protected lateinit var dataBinding: T

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dataBinding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        return dataBinding.root
    }
}