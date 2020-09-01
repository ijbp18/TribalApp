package com.home.tribalapp.presentation.userprofile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.home.tribalapp.domain.Repository
import com.home.tribalapp.presentation.favoriteImages.viewmodel.FavoriteListViewModel

/**
 * View model factory for the photo screen.
 * This will use the repository to create the view model.
 */
class UserImageListViewModelFactory constructor(private val repository: Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserImageListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserImageListViewModel(repository) as T
        } else {
            throw IllegalArgumentException("unknown model class $modelClass")
        }
    }
}
