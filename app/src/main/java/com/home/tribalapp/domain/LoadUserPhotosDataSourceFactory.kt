package com.home.tribalapp.domain

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.home.tribalapp.data.NetworkEndpoints
import com.home.tribalapp.data.model.Image

/**
 * Android paging library data source factory.
 * This will create the load photo data source.
 */
class LoadUserPhotosDataSourceFactory constructor(private val networkEndpoints: NetworkEndpoints, private val userName: String) :
    DataSource.Factory<Int, Image>() {

    val sourceLiveData = MutableLiveData<LoadUserPhotosDataSource>()

    override fun create(): DataSource<Int, Image> {
        val source = LoadUserPhotosDataSource(networkEndpoints, userName)
        sourceLiveData.postValue(source)
        return source
    }
}
