package com.home.tribalapp.data

import android.content.Context
import com.home.tribalapp.data.db.AppDataBase
import com.home.tribalapp.data.db.FavoriteDAO
import com.home.tribalapp.data.db.FavoriteDTO
import com.home.tribalapp.data.db.toDomain
import com.home.tribalapp.di.Injector
import com.home.tribalapp.domain.model.Favorite

class DataSourceImpl(context: Context) : DataSource {

    private lateinit var favoriteDAO: FavoriteDAO

    init {
        val db = AppDataBase.getInstance(context)
        db.let {
            favoriteDAO = it.favoriteDAO()
        }
    }

    override suspend fun getFavorites(): OperationResult<List<Favorite>> {
        val favoriteList =  favoriteDAO.favorites()
        favoriteList.let {favorite ->
            return if(favorite.isNotEmpty()){
                OperationResult.Success(favorite.map { favoriteDto ->  favoriteDto.toDomain() })
            }else
                OperationResult.Success(emptyList())
        }
    }

    override suspend fun addFavorite(favorite: FavoriteDTO) {
        return favoriteDAO.addFavorite(favorite)
    }

    override suspend fun removeFavorite(favorite: FavoriteDTO) {
        return favoriteDAO.removeFavorite(favorite)
    }

    override suspend fun deleteFavorites() {
        return favoriteDAO.deleteAll()
    }
}