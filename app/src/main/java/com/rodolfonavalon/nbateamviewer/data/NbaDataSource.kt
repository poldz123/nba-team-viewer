package com.rodolfonavalon.nbateamviewer.data

import com.rodolfonavalon.nbateamviewer.model.Team
import io.reactivex.Single

interface NbaDataSource {
    fun getTeams(): Single<List<Team>>
}
