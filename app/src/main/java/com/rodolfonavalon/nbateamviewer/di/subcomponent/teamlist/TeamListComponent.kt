package com.rodolfonavalon.nbateamviewer.di.subcomponent.teamlist

import com.rodolfonavalon.nbateamviewer.di.subcomponent.teamlist.module.TeamListModule
import com.rodolfonavalon.nbateamviewer.view.TeamListActivity
import dagger.Subcomponent

@Subcomponent(modules = [TeamListModule::class])
interface TeamListComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): TeamListComponent
    }

    fun inject(activity: TeamListActivity)
}