package com.home.tribalapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Image(
    val id: String,
    val created_at: String,
    val width: Int,
    val height: Int,
    val color: String = "#000000",
    val likes: Int,
    val description: String = "",
    val urls: ImageUrls,
    val links: ImageLinks,
    val user: ImageUser,
    var isSelected :Boolean = false
) : Parcelable
