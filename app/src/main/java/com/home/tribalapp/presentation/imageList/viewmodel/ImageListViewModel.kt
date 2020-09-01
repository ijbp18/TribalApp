package com.home.tribalapp.presentation.imageList.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.home.tribalapp.UnsplashPhotoPicker
import com.home.tribalapp.data.model.Image
import com.home.tribalapp.data.repositoryDB.FavoriteRepository
import com.home.tribalapp.domain.Repository
import com.home.tribalapp.presentation.base.BaseViewModel
import com.home.tribalapp.presentation.favoriteImages.viewmodel.FavoriteListViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

/**
 * View model for the picker screen.
 * This will use the repository to fetch the photos depending on the search criteria.
 * This is using rx binding.
 */
class ImageListViewModel constructor(private val repository: Repository, private val favoriteRepository: FavoriteRepository) : BaseViewModel() {

    private val mPhotosLiveData = MutableLiveData<PagedList<Image>>()
    val photosLiveData: LiveData<PagedList<Image>> get() = mPhotosLiveData

    private val _isSaveSuccessfully = MutableLiveData<Boolean>()
    val isSaveSuccessfully: LiveData<Boolean> get() = _isSaveSuccessfully

    override fun getTag(): String {
        return FavoriteListViewModel::class.java.simpleName
    }

//    init {
//        retrieveImages()
//    }

    /**
     * Binds the edit text using rx binding to listen to text change.
     *
     * @param editText the edit text to listen to
     */
    fun bindSearch(query: String) {

        mLoadingLiveData.postValue(true)

        repository.searchPhotos(query, UnsplashPhotoPicker.getPageSize())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .debounce(500, TimeUnit.MILLISECONDS)
            .subscribe(object : BaseObserver<PagedList<Image>>() {
                override fun onSuccess(data: PagedList<Image>?) {
                    mPhotosLiveData.postValue(data)
                }
            })

    }

    fun retrieveImages() {
        mLoadingLiveData.postValue(true)

        repository.loadPhotos( UnsplashPhotoPicker.getPageSize())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .debounce(500, TimeUnit.MILLISECONDS)
            .subscribe(object : BaseObserver<PagedList<Image>>() {
                override fun onSuccess(data: PagedList<Image>?) {
                    mPhotosLiveData.postValue(data)
                }
            })
    }

    /**
     * To abide by the API guidelines,
     * you need to trigger a GET request to this endpoint every time your application performs a download of a photo
     *
     * @param photos the list of selected photos
     */
    fun trackDownloads(photos: ArrayList<Image>) {
        for (photo in photos) {
            repository.trackDownload(photo.links.download_location)
        }
    }

    fun saveFavoriteItem(item: Image) {
        viewModelScope.launch {
            favoriteRepository.addFavorite(item)
        }
        _isSaveSuccessfully.value = true
    }
}
