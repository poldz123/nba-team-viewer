package com.rodolfonavalon.nbateamviewer.di.subcomponent.teampage.module

import androidx.lifecycle.ViewModel
import com.rodolfonavalon.nbateamviewer.di.appcomponent.module.ViewModelKey
import com.rodolfonavalon.nbateamviewer.viewmodel.TeamListViewModel
import com.rodolfonavalon.nbateamviewer.viewmodel.TeamPageViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class TeamPageModule {
    @Binds
    @IntoMap
    @ViewModelKey(TeamPageViewModel::class)
    abstract fun bindViewModule(viewModule: TeamPageViewModel): ViewModel
}