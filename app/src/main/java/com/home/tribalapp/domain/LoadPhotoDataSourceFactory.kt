package com.home.tribalapp.domain

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.home.tribalapp.data.NetworkEndpoints
import com.home.tribalapp.data.model.Image

/**
 * Android paging library data source factory.
 * This will create the load photo data source.
 */
class LoadPhotoDataSourceFactory constructor(private val networkEndpoints: NetworkEndpoints) :
    DataSource.Factory<Int, Image>() {

    val sourceLiveData = MutableLiveData<LoadPhotoDataSource>()

    override fun create(): DataSource<Int, Image> {
        val source = LoadPhotoDataSource(networkEndpoints)
        sourceLiveData.postValue(source)
        return source
    }
}
