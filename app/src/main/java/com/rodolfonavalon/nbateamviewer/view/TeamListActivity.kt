package com.rodolfonavalon.nbateamviewer.view

import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rodolfonavalon.nbateamviewer.R
import com.rodolfonavalon.nbateamviewer.di.appcomponent.AppComponent
import com.rodolfonavalon.nbateamviewer.model.Team
import com.rodolfonavalon.nbateamviewer.view.adapter.TeamAdapter
import com.rodolfonavalon.nbateamviewer.viewmodel.TeamListViewModel

class TeamListActivity : BaseActivity(layoutRes = R.layout.activity_team_list) {

    private val viewModel by viewModels<TeamListViewModel> { viewModelFactory }

    private lateinit var teamRecyclerView: RecyclerView
    private lateinit var teamLayoutManager: RecyclerView.LayoutManager
    private lateinit var teamAdapter: TeamAdapter

    override fun onInject(appComponent: AppComponent) {
        appComponent.teamListComponent().create().inject(this)
    }

    override fun onSetup() {
        // Setup views
        teamLayoutManager = LinearLayoutManager(this)
        teamRecyclerView = findViewById<RecyclerView>(R.id.recycler_teams).apply {
            layoutManager = teamLayoutManager
            teamAdapter = TeamAdapter()
            adapter = teamAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
        // Fetch the teams
        viewModel.fetchTeams(::onFetchTeamsSuccess, ::onFetchTeamsError)
    }

    private fun onFetchTeamsSuccess(teams: List<Team>) {
        teamAdapter.addAll(teams)
    }

    private fun onFetchTeamsError(error: Throwable) {
        showErrorDialog("Failed to fetch teams") {
            viewModel.fetchTeams(::onFetchTeamsSuccess, ::onFetchTeamsError)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_teamlist_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_teamlist_sort -> {
                val popupMenu = PopupMenu(this, findViewById(R.id.menu_teamlist_sort))
                popupMenu.inflate(R.menu.menu_teamlist_sort)
                popupMenu.setOnMenuItemClickListener(::onOptionsItemSelected)
                popupMenu.show()
            }
            R.id.menu_teamlist_sort_name -> {
                teamAdapter.sort { teams -> teams.sortedBy { it.fullName } }
            }
            R.id.menu_teamlist_sort_name_desc -> {
                teamAdapter.sort { teams -> teams.sortedByDescending { it.fullName } }
            }
            R.id.menu_teamlist_sort_wins -> {
                teamAdapter.sort { teams -> teams.sortedBy { it.wins } }
            }
            R.id.menu_teamlist_sort_wins_desc -> {
                teamAdapter.sort { teams -> teams.sortedByDescending { it.wins } }
            }
            R.id.menu_teamlist_sort_losses -> {
                teamAdapter.sort { teams -> teams.sortedBy { it.losses } }
            }
            R.id.menu_teamlist_sort_losses_desc -> {
                teamAdapter.sort { teams -> teams.sortedByDescending { it.losses } }
            }
        }
        return true
    }
}
