package com.home.tribalapp.domain

import android.util.Log
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.home.tribalapp.UnsplashPhotoPicker
import com.home.tribalapp.data.NetworkEndpoints
import com.home.tribalapp.data.model.Image
import io.reactivex.CompletableObserver
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Simple repository used as a proxy by the view models to fetch data.
 */
class Repository constructor(private val networkEndpoints: NetworkEndpoints) {

    fun loadPhotos(pageSize: Int): Observable<PagedList<Image>> {
        return RxPagedListBuilder(
            LoadPhotoDataSourceFactory(networkEndpoints),
            PagedList.Config.Builder()
                .setInitialLoadSizeHint(pageSize)
                .setPageSize(pageSize)
                .build()
        ).buildObservable()
    }

    fun loadPhotosByUser(userName: String, pageSize: Int): Observable<PagedList<Image>> {
        return RxPagedListBuilder(
            LoadUserPhotosDataSourceFactory(networkEndpoints, userName),
            PagedList.Config.Builder()
                .setInitialLoadSizeHint(pageSize)
                .setPageSize(pageSize)
                .build()
        ).buildObservable()
    }

    fun searchPhotos(criteria: String, pageSize: Int): Observable<PagedList<Image>> {
        return RxPagedListBuilder(
            SearchPhotoDataSourceFactory(networkEndpoints, criteria),
            PagedList.Config.Builder()
                .setInitialLoadSizeHint(pageSize)
                .setPageSize(pageSize)
                .build()
        ).buildObservable()
    }

    fun trackDownload(url: String?) {
        if (url != null) {
            val authUrl = url + "?client_id=" + UnsplashPhotoPicker.getAccessKey()
            networkEndpoints.trackDownload(authUrl)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(object : CompletableObserver {
                    override fun onComplete() { /* do nothing */
                    }

                    override fun onSubscribe(d: Disposable?) {  /* do nothing */
                    }

                    override fun onError(e: Throwable?) {
                        Log.e(Repository::class.java.simpleName, e?.message, e)
                    }
                })
        }
    }
}
