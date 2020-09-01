package com.home.tribalapp.presentation.imageList.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.home.tribalapp.data.repositoryDB.FavoriteRepository
import com.home.tribalapp.data.repositoryDB.FavoriteRepositoryImpl
import com.home.tribalapp.domain.Repository

/**
 * View model factory for the photo screen.
 * This will use the repository to create the view model.
 */
class ImageListViewModelFactory constructor(private val repository: Repository,private val repositoryDb: FavoriteRepository ) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ImageListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ImageListViewModel(repository, repositoryDb) as T
        } else {
            throw IllegalArgumentException("unknown model class $modelClass")
        }
    }
}
