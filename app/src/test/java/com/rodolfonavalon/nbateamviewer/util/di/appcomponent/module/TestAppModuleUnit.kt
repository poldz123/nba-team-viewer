package com.rodolfonavalon.nbateamviewer.util.di.appcomponent.module

import android.content.Context
import com.rodolfonavalon.nbateamviewer.Utils
import com.rodolfonavalon.nbateamviewer.data.NbaDataSource
import com.rodolfonavalon.nbateamviewer.di.appcomponent.SubcomponentModule
import com.rodolfonavalon.nbateamviewer.di.appcomponent.module.AppModule
import com.rodolfonavalon.nbateamviewer.di.appcomponent.module.ViewModelBuilderModule
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

class TestAppModuleUnit constructor(private val url: String) : AppModule() {
    override fun provideRetrofitBaseUrl(): String {
        return url
    }

    override fun provideClient(appContext: Context?): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }
}