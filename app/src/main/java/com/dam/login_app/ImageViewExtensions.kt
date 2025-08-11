package com.dam.login_app

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.dam.login_app.R

fun ImageView.loadImage(
    url: String?,
    placeholder: Int = R.drawable.ic_user_placeholder
) {
    Glide.with(context)
        .load(url)
        .placeholder(placeholder)
        .into(this)
}