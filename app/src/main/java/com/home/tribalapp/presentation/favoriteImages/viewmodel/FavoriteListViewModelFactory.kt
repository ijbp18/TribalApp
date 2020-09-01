package com.home.tribalapp.presentation.favoriteImages.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.home.tribalapp.data.repositoryDB.FavoriteRepository
import com.home.tribalapp.domain.Repository

/**
 * View model factory for the photo screen.
 * This will use the repository to create the view model.
 */
class FavoriteListViewModelFactory constructor(private val repository: FavoriteRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoriteListViewModel(repository) as T
        } else {
            throw IllegalArgumentException("unknown model class $modelClass")
        }
    }
}
