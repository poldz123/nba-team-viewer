package com.rodolfonavalon.nbateamviewer.view

import android.util.Log
import androidx.activity.viewModels
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
        teamLayoutManager = LinearLayoutManager(this)
        teamRecyclerView = findViewById<RecyclerView>(R.id.recycler_teams).apply {
            layoutManager = teamLayoutManager
            teamAdapter = TeamAdapter()
            adapter = teamAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
        viewModel.fetchTeams(::onFetchTeamsSuccess, ::onFetchTeamsError)
    }

    private fun onFetchTeamsSuccess(teams: List<Team>) {
        teamAdapter.addAll(teams)
    }

    private fun onFetchTeamsError(error: Throwable) {
        Log.d("", "")
    }
}