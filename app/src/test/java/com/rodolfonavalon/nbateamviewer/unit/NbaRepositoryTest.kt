package com.rodolfonavalon.nbateamviewer.unit

import com.google.common.truth.Truth.assertThat
import com.rodolfonavalon.nbateamviewer.data.NbaRepository
import com.rodolfonavalon.nbateamviewer.data.local.NbaLocalDataSource
import com.rodolfonavalon.nbateamviewer.data.remote.NbaApi
import com.rodolfonavalon.nbateamviewer.data.remote.NbaRemoteDataSource
import com.rodolfonavalon.nbateamviewer.model.Team
import com.rodolfonavalon.nbateamviewer.util.BaseMockServerTest
import com.rodolfonavalon.nbateamviewer.util.di.appcomponent.DaggerTestAppComponent
import com.rodolfonavalon.nbateamviewer.util.di.appcomponent.module.TestAppModule
import io.reactivex.rxkotlin.subscribeBy
import org.junit.Test
import retrofit2.HttpException
import javax.inject.Inject

class NbaRepositoryTest : BaseMockServerTest() {

    @Inject
    lateinit var repository: NbaRepository

    override fun setup() {
        super.setup()
        DaggerTestAppComponent.factory().create(null, TestAppModule(server.url("").toString())).into(this)
    }

    @Test
    fun testGetTeams_multipleTeams() {
        server.addResponsePath("/scoremedia/nba-team-viewer/master/input.json", "/nba_multiple_team")
        val (testTeams, testError) = testGetTeams()
        assertThat(testTeams).isNotNull()
        assertThat(testTeams).isNotEmpty()
        assertThat(testTeams).hasSize(30)
        assertThat(testError).isNull()
    }

    @Test
    fun testGetTeams_singleTeams() {
        server.addResponsePath("/scoremedia/nba-team-viewer/master/input.json", "/nba_single_team")
        val (testTeams, testError) = testGetTeams()
        assertThat(testTeams).isNotNull()
        assertThat(testTeams).isNotEmpty()
        assertThat(testTeams).hasSize(1)
        assertThat(testError).isNull()
    }

    @Test
    fun testGetTeams_emptyTeams() {
        server.addResponsePath("/scoremedia/nba-team-viewer/master/input.json", "/nba_no_team")
        val (testTeams, testError) = testGetTeams()
        assertThat(testTeams).isNotNull()
        assertThat(testTeams).isEmpty()
        assertThat(testError).isNull()
    }

    @Test
    fun testGetTeams_alphabeticallySorted() {
        // By default all of the teams are alphabetically sorted, other
        // sorting such as wins and losses are manually done through the adapter
        // and not within the repository.
        server.addResponsePath("/scoremedia/nba-team-viewer/master/input.json", "/nba_multiple_team")
        val (testTeams, testError) = testGetTeams()
        assertThat(testTeams).isNotNull()
        assertThat(testTeams).isNotEmpty()
        assertThat(testTeams).isOrdered(Comparator<Team> { t1, t2 ->
            // Check that the list is alphabetically ordered
            t1.fullName.compareTo(t2.fullName)
        })
        assertThat(testTeams).hasSize(30)
        assertThat(testError).isNull()
    }

    @Test
    fun testGetTeam_multiplePlayers() {
        server.addResponsePath("/scoremedia/nba-team-viewer/master/input.json", "/nba_single_team_multiple_players")
        val (testTeam, testError) = testGetTeam(1)
        assertThat(testTeam).isNotNull()
        assertThat(testTeam?.players).isNotEmpty()
        assertThat(testTeam?.players).hasSize(17)
        assertThat(testError).isNull()
    }

    @Test
    fun testGetTeam_singlePlayers() {
        server.addResponsePath("/scoremedia/nba-team-viewer/master/input.json", "/nba_single_team_single_players")
        val (testTeam, testError) = testGetTeam(1)
        assertThat(testTeam).isNotNull()
        assertThat(testTeam?.players).isNotEmpty()
        assertThat(testTeam?.players).hasSize(1)
        assertThat(testError).isNull()
    }

    @Test
    fun testGetTeam_emptyPlayers() {
        server.addResponsePath("/scoremedia/nba-team-viewer/master/input.json", "/nba_single_team_no_players")
        val (testTeam, testError) = testGetTeam(1)
        assertThat(testTeam).isNotNull()
        assertThat(testTeam?.players).isNotNull()
        assertThat(testTeam?.players).isEmpty()
        assertThat(testError).isNull()
    }

    @Test
    fun testGetTeam_missingTeam() {
        server.addResponsePath("/scoremedia/nba-team-viewer/master/input.json", "/nba_single_team_no_players")
        val (testTeam, testError) = testGetTeam(2)
        assertThat(testTeam).isNull()
        assertThat(testError).isNotNull()
    }

    @Test
    fun testGetTeams_500error() {
        val responseCode = 500
        server.addResponse("/scoremedia/nba-team-viewer/master/input.json", responseCode)
        val (testTeams, testError) = testGetTeams()
        assertThat(testTeams).isNull()
        assertThat(testError).isNotNull()
        assertThat(testError).isInstanceOf(HttpException::class.java)
        val httpError = testError as HttpException
        assertThat(httpError.code()).isEqualTo(responseCode)
    }

    @Test
    fun testGetTeam_500error() {
        val responseCode = 500
        server.addResponse("/scoremedia/nba-team-viewer/master/input.json", responseCode)
        val (testTeam, testError) = testGetTeam(1)
        assertThat(testTeam).isNull()
        assertThat(testError).isNotNull()
        assertThat(testError).isInstanceOf(HttpException::class.java)
        val httpError = testError as HttpException
        assertThat(httpError.code()).isEqualTo(responseCode)
    }

    private fun testGetTeams(): Pair<List<Team>?, Throwable?> {
        var testTeams: List<Team>? = null
        var testError: Throwable? = null
        repository.getTeams().subscribeBy(onSuccess = {
            testTeams = it
        }, onError = {
            testError = it
        })
        return Pair(testTeams, testError)
    }

    private fun testGetTeam(teamId: Int): Pair<Team?, Throwable?> {
        var testTeams: Team? = null
        var testError: Throwable? = null
        repository.getTeam(teamId).subscribeBy(onSuccess = {
            testTeams = it
        }, onError = {
            testError = it
        })
        return Pair(testTeams, testError)
    }
}