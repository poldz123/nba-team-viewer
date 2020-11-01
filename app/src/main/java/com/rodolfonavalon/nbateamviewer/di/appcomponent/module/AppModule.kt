package com.rodolfonavalon.nbateamviewer.di.appcomponent.module

import com.rodolfonavalon.nbateamviewer.data.NbaDataSource
import com.rodolfonavalon.nbateamviewer.data.local.NbaLocalDataSource
import com.rodolfonavalon.nbateamviewer.data.remote.NbaApi
import com.rodolfonavalon.nbateamviewer.data.remote.NbaRemoteDataSource
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
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
    fun provideClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .build()
    }

    @Provides
    @Singleton
    fun provideConverter(): Converter.Factory {
        return MoshiConverterFactory.create(Moshi.Builder().build())
    }
}