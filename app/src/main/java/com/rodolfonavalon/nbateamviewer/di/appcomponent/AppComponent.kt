package com.rodolfonavalon.nbateamviewer.di.appcomponent

import android.content.Context
import com.rodolfonavalon.nbateamviewer.di.appcomponent.module.AppModule
import com.rodolfonavalon.nbateamviewer.di.appcomponent.module.ViewModelBuilderModule
import com.rodolfonavalon.nbateamviewer.di.subcomponent.teamlist.TeamListComponent
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        ViewModelBuilderModule::class,
        SubcomponentModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }

    fun teamListComponent(): TeamListComponent.Factory
}

@Module(
    subcomponents = [
        TeamListComponent::class
    ]
)
object SubcomponentModule