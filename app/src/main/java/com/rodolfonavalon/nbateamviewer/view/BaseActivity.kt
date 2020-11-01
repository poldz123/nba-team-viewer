package com.rodolfonavalon.nbateamviewer.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rodolfonavalon.nbateamviewer.NbaApplication
import com.rodolfonavalon.nbateamviewer.data.NbaRepository
import com.rodolfonavalon.nbateamviewer.di.appcomponent.AppComponent
import com.rodolfonavalon.nbateamviewer.viewmodel.TeamListViewModel
import javax.inject.Inject

abstract class BaseActivity(@LayoutRes private val layoutRes: Int): AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    abstract fun onInject(appComponent: AppComponent)
    abstract fun onSetup()
    override fun onCreate(savedInstanceState: Bundle?) {
        onInject((applicationContext as NbaApplication).appComponent)
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
        onSetup()
    }
}