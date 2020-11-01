package com.rodolfonavalon.nbateamviewer.data.local

import com.rodolfonavalon.nbateamviewer.data.NbaDataSource
import com.rodolfonavalon.nbateamviewer.model.Player
import com.rodolfonavalon.nbateamviewer.model.Team
import io.reactivex.Observable
import io.reactivex.Single

class NbaLocalDataSource: NbaDataSource {

    override fun getTeams(): Single<List<Team>> {
        TODO("Not yet implemented")
    }
}