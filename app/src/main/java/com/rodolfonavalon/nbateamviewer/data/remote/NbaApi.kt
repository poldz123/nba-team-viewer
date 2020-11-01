package com.rodolfonavalon.nbateamviewer.data.remote

import com.rodolfonavalon.nbateamviewer.model.Team
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET

interface NbaApi {
    @GET("scoremedia/nba-team-viewer/master/input.json")
    fun getTeams(): Single<List<Team>>
}