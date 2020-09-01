package com.home.tribalapp.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Joao Betancourth on 01,septiembre,2020
 */
@Parcelize
data class Favorite(
    var id: Int = 0,
    var itemId: String = "",
    val created_at: String = "",
    var width: Int = 0,
    var height: Int = 0,
    val color: String = "",
    val likes: Int = 0,
    val description: String = "",
    val url: String = "",
    var link: String = "",
    var user: String = "",
    var userUrl: String = ""

): Parcelable