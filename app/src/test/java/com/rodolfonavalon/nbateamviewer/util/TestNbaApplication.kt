package com.rodolfonavalon.nbateamviewer.util

import com.rodolfonavalon.nbateamviewer.NbaApplication
import com.rodolfonavalon.nbateamviewer.di.appcomponent.AppComponent
import com.rodolfonavalon.nbateamviewer.di.appcomponent.DaggerAppComponent
import com.rodolfonavalon.nbateamviewer.util.di.appcomponent.DaggerTestAppComponent
import com.rodolfonavalon.nbateamviewer.util.di.appcomponent.module.TestAppModule

class TestNbaApplication: NbaApplication() {
    lateinit var url: String
    override val appComponent: AppComponent by lazy {
        DaggerTestAppComponent.factory().create(applicationContext, TestAppModule(url))
    }

    companion object {

    }
}