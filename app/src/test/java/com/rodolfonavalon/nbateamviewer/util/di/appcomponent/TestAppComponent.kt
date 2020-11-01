package com.rodolfonavalon.nbateamviewer.util.di.appcomponent

import android.content.Context
import com.rodolfonavalon.nbateamviewer.di.appcomponent.AppComponent
import com.rodolfonavalon.nbateamviewer.di.appcomponent.SubcomponentModule
import com.rodolfonavalon.nbateamviewer.di.appcomponent.module.AppModule
import com.rodolfonavalon.nbateamviewer.di.appcomponent.module.ViewModelBuilderModule
import com.rodolfonavalon.nbateamviewer.unit.NbaRepositoryTest
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        ViewModelBuilderModule::class,
        SubcomponentModule::class
    ]
)
interface TestAppComponent : AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context?, appModule: AppModule): TestAppComponent
    }
    fun into(baseTest: NbaRepositoryTest)
}