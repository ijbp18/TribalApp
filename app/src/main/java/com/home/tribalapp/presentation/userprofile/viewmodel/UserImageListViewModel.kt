package com.home.tribalapp.presentation.userprofile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.home.tribalapp.UnsplashPhotoPicker
import com.home.tribalapp.data.model.Image
import com.home.tribalapp.domain.Repository
import com.home.tribalapp.presentation.base.BaseViewModel
import com.home.tribalapp.presentation.favoriteImages.viewmodel.FavoriteListViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * View model for the picker screen.
 * This will use the repository to fetch the photos depending on the search criteria.
 * This is using rx binding.
 */
class UserImageListViewModel constructor(private val repository: Repository) : BaseViewModel() {

    private val mPhotosLiveData = MutableLiveData<PagedList<Image>>()
    val photosLiveData: LiveData<PagedList<Image>> get() = mPhotosLiveData

    override fun getTag(): String {
        return FavoriteListViewModel::class.java.simpleName
    }

    fun retrieveUserData(userName: String) {
        mLoadingLiveData.postValue(true)
        repository.loadPhotosByUser(userName, UnsplashPhotoPicker.getPageSize())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .debounce(500, TimeUnit.MILLISECONDS)
            .subscribe(object : BaseObserver<PagedList<Image>>() {
                override fun onSuccess(data: PagedList<Image>?) {
                    mPhotosLiveData.postValue(data)
                }
            })
    }

}
