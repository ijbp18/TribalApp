package com.home.tribalapp.data.model

import android.os.Parcelable
import com.home.tribalapp.data.model.ImageLinks
import com.home.tribalapp.data.model.ImageUrls
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImageUser(
    val id: String,
    val username: String,
    val name: String,
    val portfolio_url: String?,
    val bio: String?,
    val location: String?,
    val total_likes: Int,
    val total_photos: Int,
    val total_collection: Int,
    val profile_image: ImageUrls,
    val links: ImageLinks,
    val instagram_username: String,
    val twitter_username: String
) : Parcelable
