package com.dam.p1_alten_felixcorraltejero

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImage(
    url: String?,
    placeholder: Int = R.drawable.ic_user_placeholder
) {
    Glide.with(context)
        .load(url)
        .placeholder(placeholder)
        .into(this)
}