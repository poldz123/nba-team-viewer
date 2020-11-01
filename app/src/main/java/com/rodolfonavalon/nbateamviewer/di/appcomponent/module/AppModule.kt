package com.rodolfonavalon.nbateamviewer.di.appcomponent.module

import android.content.Context
import com.rodolfonavalon.nbateamviewer.Utils
import com.rodolfonavalon.nbateamviewer.data.NbaDataSource
import com.rodolfonavalon.nbateamviewer.data.local.NbaLocalDataSource
import com.rodolfonavalon.nbateamviewer.data.remote.NbaApi
import com.rodolfonavalon.nbateamviewer.data.remote.NbaRemoteDataSource
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
class AppModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class RemoteDataSource

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class LocalDataSource

    @Provides
    @RemoteDataSource
    @Singleton
    fun provideRemoteDataSource(service: NbaApi): NbaDataSource {
        return NbaRemoteDataSource(service)
    }

    @Provides
    @LocalDataSource
    @Singleton
    fun provideLocalDataSource(): NbaDataSource {
        return NbaLocalDataSource()
    }

    @Provides
    @Singleton
    fun provideService(client: OkHttpClient, converter: Converter.Factory): NbaApi {
        return Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/")
            .addConverterFactory(converter)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
            .create(NbaApi::class.java)
    }

    @Provides
    @Singleton
    fun provideClient(appContext: Context): OkHttpClient {
        // Create a cache with the size of 10 Mib
        val cacheDir = File(appContext.cacheDir, "httpCache")
        val cache = Cache(cacheDir, 10 * 1024 * 1024)
        // Create the client
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val originalResponse = chain.proceed(chain.request())
                return@addInterceptor if (Utils.isConnected(appContext)) {
                    // Cache the response for 5 minutes only when connected
                    val maxAge = 60 * 5
                    originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=$maxAge")
                        .build()
                } else {
                    // Offline mode should cache the response for longer time period.
                    val maxStale = 60 * 60 * 24 * 28
                    originalResponse.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                        .build()
                }
            }
            .cache(cache)
            .build()
    }

    @Provides
    @Singleton
    fun provideConverter(): Converter.Factory {
        return MoshiConverterFactory.create(Moshi.Builder().build())
    }
}