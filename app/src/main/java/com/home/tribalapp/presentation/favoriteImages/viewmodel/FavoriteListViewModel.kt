package com.home.tribalapp.presentation.favoriteImages.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.home.tribalapp.UnsplashPhotoPicker
import com.home.tribalapp.data.OperationResult
import com.home.tribalapp.data.model.Image
import com.home.tribalapp.data.repositoryDB.FavoriteRepository
import com.home.tribalapp.domain.Repository
import com.home.tribalapp.domain.model.Favorite
import com.home.tribalapp.presentation.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

/**
 * View model for the picker screen.
 * This will use the repository to fetch the photos depending on the search criteria.
 */
class FavoriteListViewModel constructor(private val repository: FavoriteRepository) :
    BaseViewModel() {

    private val mPhotosLiveData = MutableLiveData<OperationResult<List<Favorite>>>()
    val photosLiveData: LiveData<OperationResult<List<Favorite>>> get() = mPhotosLiveData

    override fun getTag(): String {
        return FavoriteListViewModel::class.java.simpleName
    }

    fun retrieveFavoriteItems() {
        mPhotosLiveData.value = OperationResult.Loading()
        viewModelScope.launch {
            mPhotosLiveData.value = with(Dispatchers.IO) { repository.getFavorites() }
        }
    }

    fun removeFavorite(item: Favorite) {
        viewModelScope.launch {
            repository.deleteFavorite(item)
        }
    }

}
