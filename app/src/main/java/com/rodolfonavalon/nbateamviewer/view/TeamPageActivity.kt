package com.rodolfonavalon.nbateamviewer.view

import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rodolfonavalon.nbateamviewer.R
import com.rodolfonavalon.nbateamviewer.di.appcomponent.AppComponent
import com.rodolfonavalon.nbateamviewer.model.Team
import com.rodolfonavalon.nbateamviewer.view.adapter.PlayerAdapter
import com.rodolfonavalon.nbateamviewer.viewmodel.TeamPageViewModel

class TeamPageActivity : BaseActivity(layoutRes = R.layout.activity_team_page) {

    private val viewModel by viewModels<TeamPageViewModel> { viewModelFactory }

    private lateinit var playerRecyclerView: RecyclerView
    private lateinit var playerLayoutManager: RecyclerView.LayoutManager
    private lateinit var playerAdapter: PlayerAdapter

    private var teamId: Int = -1

    override fun onInject(appComponent: AppComponent) {
        appComponent.teamPageComponent().create().inject(this)
    }

    override fun onSetup() {
        // Set toolbar
        toolbar.title = ""
        // Setup views
        playerLayoutManager = LinearLayoutManager(this)
        playerRecyclerView = findViewById<RecyclerView>(R.id.recycler_players).apply {
            layoutManager = playerLayoutManager
            playerAdapter = PlayerAdapter()
            adapter = playerAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
        // Fetch the team
        teamId = intent.getIntExtra(INTENT_KEY_TEAM_ID, -1)
        viewModel.fetchTeam(teamId, ::onFetchTeamSuccess, ::onFetchTeamError)
    }

    private fun onFetchTeamSuccess(team: Team) {
        toolbar.title = team.fullName
        toolbar.subtitle = getString(R.string.team_wins_losses, team.wins, team.losses)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        playerAdapter.addAll(team.players)
    }

    private fun onFetchTeamError(error: Throwable) {
        showErrorDialog("Failed to fetch team: $teamId") {
            viewModel.fetchTeam(teamId, ::onFetchTeamSuccess, ::onFetchTeamError)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        // Closes the activity when back button is clicked
        finish()
        return super.onSupportNavigateUp()
    }

    companion object {
        const val INTENT_KEY_TEAM_ID = "INTENT_KEY_TEAM_ID"
    }
}
