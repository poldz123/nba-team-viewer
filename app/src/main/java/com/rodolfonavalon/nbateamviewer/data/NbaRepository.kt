package com.rodolfonavalon.nbateamviewer.data

import com.rodolfonavalon.nbateamviewer.di.appcomponent.module.AppModule
import com.rodolfonavalon.nbateamviewer.model.Team
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NbaRepository @Inject constructor(
    @AppModule.RemoteDataSource
    private val nbaRemoteDataSource: NbaDataSource,
    @AppModule.LocalDataSource
    private val nbaLocalDataSource: NbaDataSource
) {
    // Storage cache for the teams. This is useful when multiple calls to retrieve the
    // teams would prevent further network calls.
    private var cacheTeams: Map<Int, Team>? = null;

    fun getTeams(): Single<List<Team>> {
        val cacheTeams = this.cacheTeams
        return if (cacheTeams != null) {
            // If the cache exist then just return it instead of doing another network call
            Single.just(cacheTeams.values.toList()).observeOn(AndroidSchedulers.mainThread())
        } else {
            nbaRemoteDataSource.getTeams()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }.map { teams ->
            if (cacheTeams == null) {
                // Cache the teams in a map to easily get each of the teams
                this.cacheTeams = teams.associateBy({it.id}, {it})
            }
            // Sort the teams in alphabetical order by default
            teams.sortedBy { it.fullName }
        }
    }

    fun getPlayers(forTeam: Team, onSuccess: () -> List<Team>, onError: () -> Unit): Disposable {
//        var cacheTeams = this.cacheTeams
//        if (cacheTeams == null) {
//            getTeams()
//        } else if (cacheTeams.containsKey(forTeam.id)) else {
//            Observable.just(cacheTeams[]).subscribe(onSuccess)
//        } else {
//            Observable.error(NoClassDefFoundError)
//        }
        TODO("Not yet implemented")
    }
}