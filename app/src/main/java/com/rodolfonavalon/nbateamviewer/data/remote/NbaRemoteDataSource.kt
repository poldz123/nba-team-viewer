package com.rodolfonavalon.nbateamviewer.data.remote

import com.rodolfonavalon.nbateamviewer.data.NbaDataSource
import com.rodolfonavalon.nbateamviewer.model.Player
import com.rodolfonavalon.nbateamviewer.model.Team
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class NbaRemoteDataSource constructor(private val service: NbaApi): NbaDataSource {

    override fun getTeams(): Single<List<Team>> {
        return service.getTeams()
    }
}