package com.home.tribalapp.di

import android.content.Context
import com.home.tribalapp.UnsplashPhotoPicker
import com.home.tribalapp.data.DataSourceImpl
import com.home.tribalapp.data.NetworkEndpoints
import com.home.tribalapp.data.repositoryDB.FavoriteRepositoryImpl
import com.home.tribalapp.domain.Repository
import com.home.tribalapp.presentation.imageList.viewmodel.ImageListViewModelFactory
import com.home.tribalapp.presentation.favoriteImages.viewmodel.FavoriteListViewModelFactory
import com.home.tribalapp.presentation.userprofile.viewmodel.UserImageListViewModelFactory
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Manual dependency injection to avoid sticking to a specific dependency injection library.
 */
object Injector {

    private const val CONTENT_TYPE = "Content-Type"
    private const val APPLICATION_JSON = "application/json"
    private const val ACCEPT_VERSION = "Accept-Version"

    private fun createHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                .addHeader(ACCEPT_VERSION, "v1")
                .build()
            chain.proceed(newRequest)
        }
    }

    private fun createLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    private fun createHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addNetworkInterceptor(createHeaderInterceptor())
        if (UnsplashPhotoPicker.isLoggingEnabled()) {
            builder.addNetworkInterceptor(createLoggingInterceptor())
        }
        val cacheSize = 10 * 1024 * 1024 // 10 MB of cache
        val cache = Cache(UnsplashPhotoPicker.getApplication().cacheDir, cacheSize.toLong())
        builder.cache(cache)
        return builder.build()
    }

    private fun createRetrofitBuilder(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NetworkEndpoints.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(createHttpClient())
            .build()
    }

    private fun createNetworkEndpoints(): NetworkEndpoints =
        createRetrofitBuilder().create(NetworkEndpoints::class.java)

    private fun createRepository(): Repository {
        return Repository(networkEndpoints = createNetworkEndpoints())
    }

    private lateinit var dbDataSource: DataSourceImpl

//    private fun createDataSource(context: Context): DataSourceImpl {
//        return DataSourceImpl(context)
//    }

    private fun createRepositoryDB(): FavoriteRepositoryImpl {
        return FavoriteRepositoryImpl(dbDataSource)
    }

    fun createPickerViewModelFactory(): ImageListViewModelFactory {
        return ImageListViewModelFactory(
            repository = createRepository(),
            repositoryDb = createRepositoryDB()
        )
    }

    fun createPickerUserViewModelFactory(): UserImageListViewModelFactory {
        return UserImageListViewModelFactory(createRepository())
    }

    fun createFavoriteViewModelFactory(): FavoriteListViewModelFactory {
        return FavoriteListViewModelFactory(createRepositoryDB())
    }

    fun setup(context: Context) {
        dbDataSource = DataSourceImpl(context)
    }
}
