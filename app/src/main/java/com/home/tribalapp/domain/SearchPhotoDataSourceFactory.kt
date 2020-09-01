package com.home.tribalapp.domain

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.home.tribalapp.data.NetworkEndpoints
import com.home.tribalapp.data.model.Image

/**
 * Android paging library data source factory.
 * This will create the search photo data source.
 */
class SearchPhotoDataSourceFactory constructor(
    private val networkEndpoints: NetworkEndpoints,
    private val criteria: String
) :
    DataSource.Factory<Int, Image>() {

    val sourceLiveData = MutableLiveData<SearchPhotoDataSource>()

    override fun create(): DataSource<Int, Image> {
        val source = SearchPhotoDataSource(networkEndpoints, criteria)
        sourceLiveData.postValue(source)
        return source
    }
}
