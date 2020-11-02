package com.rodolfonavalon.nbateamviewer.view

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.rodolfonavalon.nbateamviewer.NbaApplication
import com.rodolfonavalon.nbateamviewer.R
import com.rodolfonavalon.nbateamviewer.di.appcomponent.AppComponent
import javax.inject.Inject

abstract class BaseActivity(@LayoutRes private val layoutRes: Int) : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var toolbar: Toolbar

    abstract fun onInject(appComponent: AppComponent)
    abstract fun onSetup()
    override fun onCreate(savedInstanceState: Bundle?) {
        onInject((applicationContext as NbaApplication).appComponent)
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        onSetup()
    }

    fun showErrorDialog(message: String, onPositiveClick: () -> Unit) {
        val dialog: AlertDialog = AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("Retry") { dialog, _ ->
                onPositiveClick()
                dialog.dismiss()
            }
            .create()
        dialog.show()
    }
}
