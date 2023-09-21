package com.example.albertsonstask.presentation.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("app:loadUrl")
fun ImageView.loadUrl(url: String? = null) {
    url?.let {
        Glide.with(this.context)
            .load(it)
            .into(this)
    }
}