package com.rodolfonavalon.nbateamviewer

import android.app.Application
import com.rodolfonavalon.nbateamviewer.di.appcomponent.AppComponent
import com.rodolfonavalon.nbateamviewer.di.appcomponent.DaggerAppComponent

open class NbaApplication : Application() {
    open val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}