package com.home.tribalapp.data.repositoryDB

import android.view.View
import com.home.tribalapp.data.DataSourceImpl
import com.home.tribalapp.data.OperationResult
import com.home.tribalapp.data.db.FavoriteDTO
import com.home.tribalapp.data.model.Image
import com.home.tribalapp.domain.model.Favorite

class FavoriteRepositoryImpl(private val dataSource: DataSourceImpl) : FavoriteRepository {

    override suspend fun getFavorites(): OperationResult<List<Favorite>> {
        return dataSource.getFavorites()
    }

    override suspend fun addFavorite(favorite: Image) {
        var descriptionValidate = if (favorite.description == null || favorite.description.isEmpty()) ""
        else ""
        dataSource.addFavorite(FavoriteDTO(0, favorite.id, favorite.created_at, favorite.width,
            favorite.height, favorite.color, favorite.likes, descriptionValidate, favorite.urls.small,
            favorite.links.self, favorite.user.username, favorite.user.profile_image.medium!!
        ))
    }

    override suspend fun deleteFavorite(favorite: Favorite) {
        var descriptionValidate = if (favorite.description == null || favorite.description.isEmpty()) ""
        else ""
        return dataSource.removeFavorite(FavoriteDTO(0, favorite.itemId, favorite.created_at, favorite.width,
            favorite.height, favorite.color, favorite.likes, descriptionValidate, favorite.url,
            favorite.link, favorite.user, favorite.userUrl))
    }

}