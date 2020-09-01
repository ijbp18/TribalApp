package com.home.tribalapp.data

import com.home.tribalapp.data.model.SearchResponse
import com.home.tribalapp.data.model.Image
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Retrofit endpoints definition.
 */
interface NetworkEndpoints {

    @GET("collections/317099/photos")
    fun loadPhotos(
        @Query("client_id") clientId: String,
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int
    ): Observable<Response<List<Image>>>

    @GET("users/{username}/photos")
    fun loadPhotosByUser(
        @Path("username") username: String,
        @Query("client_id") clientId: String,
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int
    ): Observable<Response<List<Image>>>

    @GET("search/photos")
    fun searchPhotos(
        @Query("client_id") clientId: String,
        @Query("query") criteria: String,
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int
    ): Observable<Response<SearchResponse>>

    @GET
    fun trackDownload(@Url url: String): Completable

    companion object {
        const val BASE_URL = "https://api.unsplash.com/"
    }
}
