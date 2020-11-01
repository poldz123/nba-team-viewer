package com.rodolfonavalon.nbateamviewer.di.subcomponent.teamlist.module

import androidx.lifecycle.ViewModel
import com.rodolfonavalon.nbateamviewer.di.appcomponent.module.ViewModelKey
import com.rodolfonavalon.nbateamviewer.viewmodel.TeamListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class TeamListModule {
    @Binds
    @IntoMap
    @ViewModelKey(TeamListViewModel::class)
    abstract fun bindViewModule(viewModule: TeamListViewModel): ViewModel
}