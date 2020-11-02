package com.rodolfonavalon.nbateamviewer.ui

import android.app.Dialog
import android.os.Build
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import com.rodolfonavalon.nbateamviewer.R
import com.rodolfonavalon.nbateamviewer.model.Team
import com.rodolfonavalon.nbateamviewer.util.BaseRobolectricTest
import com.rodolfonavalon.nbateamviewer.util.TestNbaApplication
import com.rodolfonavalon.nbateamviewer.view.TeamListActivity
import com.rodolfonavalon.nbateamviewer.view.TeamPageActivity
import com.rodolfonavalon.nbateamviewer.view.adapter.TeamAdapter
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import org.robolectric.fakes.RoboMenuItem
import org.robolectric.shadows.ShadowAlertDialog
import org.robolectric.shadows.ShadowDialog
import org.robolectric.shadows.ShadowListPopupWindow
import org.robolectric.shadows.ShadowPopupMenu

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [ Build.VERSION_CODES.N ],
    manifest = Config.NONE,
    application = TestNbaApplication::class)
class TeamListActivityTest : BaseRobolectricTest() {

    override fun setup() {
        super.setup()
        ApplicationProvider.getApplicationContext<TestNbaApplication>().url = server.url("").toString()
    }

    @Test
    fun testTeams_list() {
        server.addResponsePath("/scoremedia/nba-team-viewer/master/input.json", "/nba_multiple_team")
        launchActivity<TeamListActivity> { activity ->
            val recylerView = activity.findViewById<RecyclerView>(R.id.recycler_teams)
            val adapter = recylerView.adapter!!
            assertThat(adapter.itemCount).isEqualTo(30)
        }
    }

    @Test
    fun testTeams_sort() {
        server.addResponsePath("/scoremedia/nba-team-viewer/master/input.json", "/nba_multiple_team")
        launchActivity<TeamListActivity> { activity ->
            val recylerView = activity.findViewById<RecyclerView>(R.id.recycler_teams)
            val adapter = recylerView.adapter!! as TeamAdapter
            assertThat(adapter.itemCount).isEqualTo(30)
            // Click the name sort
            activity.onOptionsItemSelected(RoboMenuItem(R.id.menu_teamlist_sort_name))
            assertThat(adapter.teams).isOrdered(Comparator<Team> { t1, t2 ->
                // Check that the list is alphabetically ordered
                t1.fullName.compareTo(t2.fullName)
            })
            // Click the wins sort
            activity.onOptionsItemSelected(RoboMenuItem(R.id.menu_teamlist_sort_wins))
            assertThat(adapter.teams).isOrdered(Comparator<Team> { t1, t2 ->
                // Check that the list is ordered by highest to lowest wins
                t2.wins.compareTo(t1.wins)
            })
            // Click the losses sort
            activity.onOptionsItemSelected(RoboMenuItem(R.id.menu_teamlist_sort_losses))
            assertThat(adapter.teams).isOrdered(Comparator<Team> { t1, t2 ->
                // Check that the list is ordered by lowest to highest losses
                t1.losses.compareTo(t2.losses)
            })
        }
    }

    @Test
    fun testTeams_item() {
        server.addResponsePath("/scoremedia/nba-team-viewer/master/input.json", "/nba_single_team")
        val expectedTeam = Team(id = 1,fullName = "Boston Celtics", wins = 45, losses = 20, players = mutableListOf())
        launchActivity<TeamListActivity> { activity ->
            val recylerView = activity.findViewById<RecyclerView>(R.id.recycler_teams)
            val adapter = recylerView.adapter!!
            assertThat(adapter.itemCount).isEqualTo(1)
            val item = recylerView.layoutManager?.findViewByPosition(0)!!
            // Check Name
            val nameText = item.findViewById<TextView>(R.id.textview_team_name)
            assertThat(nameText.text).isEqualTo(expectedTeam.fullName)
            // Check Wins and Losses
            val winsText = item.findViewById<TextView>(R.id.textview_team_wins)
            assertThat(winsText.text).isEqualTo(activity.getString(R.string.team_wins, expectedTeam.wins))
            val lossesText = item.findViewById<TextView>(R.id.textview_team_losses)
            assertThat(lossesText.text).isEqualTo(activity.getString(R.string.team_losses, expectedTeam.losses))
        }
    }

    @Test
    fun testTeams_navigate() {
        server.addResponsePath("/scoremedia/nba-team-viewer/master/input.json", "/nba_single_team")
        val expectedTeam = Team(id = 1,fullName = "Boston Celtics", wins = 45, losses = 20, players = mutableListOf())
        launchActivity<TeamListActivity> { activity ->
            val recylerView = activity.findViewById<RecyclerView>(R.id.recycler_teams)
            val adapter = recylerView.adapter!!
            assertThat(adapter.itemCount).isEqualTo(1)
            val item = recylerView.layoutManager?.findViewByPosition(0)!!
            val container = item.findViewById<ViewGroup>(R.id.container_team)
            container.performClick()
            // Check that when clicking the team it should navigate to the team page
            assertThat(getCurrentActivity(activity)).isEqualTo(TeamPageActivity::class.java)
        }
    }

    @Test
    fun testTeams_error() {
        server.addResponse("/scoremedia/nba-team-viewer/master/input.json", 500)
        val expectedTeam = Team(id = 1,fullName = "Boston Celtics", wins = 45, losses = 20, players = mutableListOf())
        launchActivity<TeamListActivity> { activity ->
            // Check that error dialog is showing
            val dialog = ShadowAlertDialog.getLatestDialog()
            assertThat(dialog.isShowing).isTrue()
        }
    }

    @Test
    fun testTeams_errorThenRetrySuccess() {
        server.addResponse("/scoremedia/nba-team-viewer/master/input.json", 500)
        server.addResponsePath("/scoremedia/nba-team-viewer/master/input.json", "/nba_multiple_team")
        val expectedTeam = Team(id = 1,fullName = "Boston Celtics", wins = 45, losses = 20, players = mutableListOf())
        launchActivity<TeamListActivity> { activity ->
            // Check that error dialog is showing
            val dialog = ShadowAlertDialog.getLatestDialog() as AlertDialog
            assertThat(dialog.isShowing).isTrue()
            // Click retry button and check that we successfully get the response from the server
            dialog.getButton(Dialog.BUTTON_POSITIVE).performClick()
            Robolectric.flushForegroundThreadScheduler()
            assertThat(dialog.isShowing).isFalse() // It should hid the dialog on retry click
            val recylerView = activity.findViewById<RecyclerView>(R.id.recycler_teams)
            val adapter = recylerView.adapter!!
            assertThat(adapter.itemCount).isEqualTo(30)
        }
    }
}