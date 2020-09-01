package com.home.tribalapp.data.repositoryDB

import com.home.tribalapp.data.OperationResult
import com.home.tribalapp.data.model.Image
import com.home.tribalapp.domain.model.Favorite

/**
 * Created by Joao Betancourth on 01,septiembre,2020
 */
interface FavoriteRepository {
    suspend fun getFavorites(): OperationResult<List<Favorite>>
    suspend fun addFavorite(favorite: Image)
    suspend fun deleteFavorite(favorite: Favorite)
}