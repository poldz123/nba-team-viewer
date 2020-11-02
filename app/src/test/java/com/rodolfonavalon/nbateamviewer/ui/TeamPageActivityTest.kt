package com.rodolfonavalon.nbateamviewer.ui

import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import com.rodolfonavalon.nbateamviewer.R
import com.rodolfonavalon.nbateamviewer.model.Player
import com.rodolfonavalon.nbateamviewer.model.Team
import com.rodolfonavalon.nbateamviewer.util.BaseRobolectricTest
import com.rodolfonavalon.nbateamviewer.util.TestNbaApplication
import com.rodolfonavalon.nbateamviewer.view.TeamListActivity
import com.rodolfonavalon.nbateamviewer.view.TeamPageActivity
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowAlertDialog

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [ Build.VERSION_CODES.N ],
    manifest = Config.NONE,
    application = TestNbaApplication::class)
class TeamPageActivityTest : BaseRobolectricTest() {

    override fun setup() {
        super.setup()
        ApplicationProvider.getApplicationContext<TestNbaApplication>().url = server.url("").toString()
    }

    @Test
    fun testPlayers_list() {
        server.addResponsePath("/scoremedia/nba-team-viewer/master/input.json", "/nba_single_team")
        val intent = Intent(ApplicationProvider.getApplicationContext(), TeamPageActivity::class.java)
        intent.putExtra(TeamPageActivity.INTENT_KEY_TEAM_ID, 1)
        launchActivity<TeamPageActivity>(intent) { activity ->
            val recylerView = activity.findViewById<RecyclerView>(R.id.recycler_players)
            val adapter = recylerView.adapter!!
            assertThat(adapter.itemCount).isEqualTo(17)
        }
    }

    @Test
    fun testPlayers_item() {
        server.addResponsePath("/scoremedia/nba-team-viewer/master/input.json", "/nba_single_team_single_players")
        val expectedPlayer = Player(
            id = 37729,
            firstName = "Kadeem",
            lastName = "Allen",
            number = 45,
            position = "SG"
        )
        val intent = Intent(ApplicationProvider.getApplicationContext(), TeamPageActivity::class.java)
        intent.putExtra(TeamPageActivity.INTENT_KEY_TEAM_ID, 1)
        launchActivity<TeamPageActivity>(intent) { activity ->
            val recylerView = activity.findViewById<RecyclerView>(R.id.recycler_players)
            val adapter = recylerView.adapter!!
            assertThat(adapter.itemCount).isEqualTo(1)
            val item = recylerView.layoutManager?.findViewByPosition(0)!!
            // Check Name
            val nameText = item.findViewById<TextView>(R.id.textview_player_name)
            assertThat(nameText.text).isEqualTo("${expectedPlayer.firstName} ${expectedPlayer.lastName}")
            // Check Wins and Losses
            val winsText = item.findViewById<TextView>(R.id.textview_player_number)
            assertThat(winsText.text).isEqualTo(activity.getString(R.string.player_number, expectedPlayer.number))
            val lossesText = item.findViewById<TextView>(R.id.textview_player_position)
            assertThat(lossesText.text).isEqualTo(activity.getString(R.string.player_position, expectedPlayer.position))
        }
    }

    @Test
    fun testPlayers_missing() {
        server.addResponsePath("/scoremedia/nba-team-viewer/master/input.json", "/nba_multiple_team")
        val intent = Intent(ApplicationProvider.getApplicationContext(), TeamPageActivity::class.java)
        intent.putExtra(TeamPageActivity.INTENT_KEY_TEAM_ID, 5000)
        launchActivity<TeamPageActivity>(intent) { activity ->
            // Check that error dialog is showing
            val dialog = ShadowAlertDialog.getLatestDialog()
            assertThat(dialog.isShowing).isTrue()
        }
    }

    @Test
    fun testPlayers_error() {
        server.addResponse("/scoremedia/nba-team-viewer/master/input.json", 500)
        val intent = Intent(ApplicationProvider.getApplicationContext(), TeamPageActivity::class.java)
        intent.putExtra(TeamPageActivity.INTENT_KEY_TEAM_ID, 1)
        launchActivity<TeamPageActivity>(intent) { activity ->
            // Check that error dialog is showing
            val dialog = ShadowAlertDialog.getLatestDialog()
            assertThat(dialog.isShowing).isTrue()
        }
    }

    @Test
    fun testPlayers_errorThenRetrySuccess() {
        server.addResponse("/scoremedia/nba-team-viewer/master/input.json", 500)
        server.addResponsePath("/scoremedia/nba-team-viewer/master/input.json", "/nba_single_team")
        val intent = Intent(ApplicationProvider.getApplicationContext(), TeamPageActivity::class.java)
        intent.putExtra(TeamPageActivity.INTENT_KEY_TEAM_ID, 1)
        launchActivity<TeamPageActivity>(intent) { activity ->
            // Check that error dialog is showing
            val dialog = ShadowAlertDialog.getLatestDialog() as AlertDialog
            assertThat(dialog.isShowing).isTrue()
            // Click retry button and check that we successfully get the response from the server
            dialog.getButton(Dialog.BUTTON_POSITIVE).performClick()
            Robolectric.flushForegroundThreadScheduler()
            assertThat(dialog.isShowing).isFalse() // It should hid the dialog on retry click
            val recylerView = activity.findViewById<RecyclerView>(R.id.recycler_players)
            val adapter = recylerView.adapter!!
            assertThat(adapter.itemCount).isEqualTo(17)
        }
    }
}