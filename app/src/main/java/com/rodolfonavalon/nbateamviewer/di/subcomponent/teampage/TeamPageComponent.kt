package com.rodolfonavalon.nbateamviewer.di.subcomponent.teampage

import com.rodolfonavalon.nbateamviewer.di.subcomponent.teamlist.module.TeamListModule
import com.rodolfonavalon.nbateamviewer.di.subcomponent.teampage.module.TeamPageModule
import com.rodolfonavalon.nbateamviewer.view.TeamListActivity
import com.rodolfonavalon.nbateamviewer.view.TeamPageActivity
import dagger.Subcomponent

@Subcomponent(modules = [TeamPageModule::class])
interface TeamPageComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): TeamPageComponent
    }

    fun inject(activity: TeamPageActivity)
}
