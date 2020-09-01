package com.home.tribalapp.data

import com.home.tribalapp.data.db.FavoriteDTO
import com.home.tribalapp.domain.model.Favorite


interface DataSource {
    suspend fun getFavorites(): OperationResult<List<Favorite>>
    suspend fun addFavorite(favorite: FavoriteDTO)
    suspend fun removeFavorite(favorite: FavoriteDTO)
    suspend fun deleteFavorites()
}