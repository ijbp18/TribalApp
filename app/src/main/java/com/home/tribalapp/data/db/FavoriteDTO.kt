package com.home.tribalapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import com.home.tribalapp.domain.model.Favorite

@Entity(tableName = "tb_favorite")
data class FavoriteDTO(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "itemId") val itemId: String,
    @ColumnInfo(name = "created_at") val created_at: String,
    @ColumnInfo(name = "width") var width: Int,
    @ColumnInfo(name = "height") var height: Int,
    @ColumnInfo(name = "color") val color: String,
    @ColumnInfo(name = "likes") val likes: Int,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "link") val link: String,
    @ColumnInfo(name = "user") val user: String,
    @ColumnInfo(name = "userUrl") val userUrl: String
)

fun FavoriteDTO.toDomain() = Favorite(
    id = id,
    itemId = itemId,
    created_at = created_at,
    width = width,
    height = height,
    color = color,
    likes = likes,
    description = description,
    url = url,
    link = link,
    user = user,
    userUrl = userUrl
)
