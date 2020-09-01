package com.home.tribalapp.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteDAO {
    @Query("SELECT * FROM tb_favorite")
    suspend fun favorites(): List<FavoriteDTO>

    @Insert
    suspend fun addFavorite(favorite: FavoriteDTO)

    @Query("DELETE FROM tb_favorite")
    suspend fun deleteAll()

    @Delete
    suspend fun removeFavorite(favorite: FavoriteDTO)
}