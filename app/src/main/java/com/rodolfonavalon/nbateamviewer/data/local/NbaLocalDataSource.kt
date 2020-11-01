package com.rodolfonavalon.nbateamviewer.data.local

import com.rodolfonavalon.nbateamviewer.data.NbaDataSource
import com.rodolfonavalon.nbateamviewer.model.Player
import com.rodolfonavalon.nbateamviewer.model.Team
import io.reactivex.Observable
import io.reactivex.Single

class NbaLocalDataSource: NbaDataSource {

    override fun getTeams(): Single<List<Team>> {
        // Did not implements this because there is no specification for data
        // persistence within the task. But to summarize this data source, this
        // would serve as the data persistence for the response using sqlite or any
        // database management system to store them locally.
        TODO("Not yet implemented")
    }
}