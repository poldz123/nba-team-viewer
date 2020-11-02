package com.rodolfonavalon.nbateamviewer.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.recyclerview.widget.RecyclerView
import com.rodolfonavalon.nbateamviewer.R
import com.rodolfonavalon.nbateamviewer.model.Team
import com.rodolfonavalon.nbateamviewer.view.TeamPageActivity
import com.rodolfonavalon.nbateamviewer.view.TeamPageActivity.Companion.INTENT_KEY_TEAM_ID

class TeamAdapter: RecyclerView.Adapter<TeamViewHolder>()
{
    @VisibleForTesting
    val teams = mutableListOf<Team>()

    fun addAll(teams: List<Team>) {
        this.teams.clear()
        this.teams.addAll(teams)
        notifyDataSetChanged()
    }

    fun clear() {
        this.teams.clear()
        notifyDataSetChanged()
    }

    fun sort(teams: (List<Team>) -> List<Team>) {
        addAll(teams(this.teams))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_team, parent, false)
        return TeamViewHolder(view)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        val team = teams[position]
        val context = holder.itemView.context
        holder.teamName.text = team.fullName
        holder.teamWins.text = context.getString(R.string.team_wins, team.wins)
        holder.teamLosses.text = context.getString(R.string.team_losses, team.losses)
        holder.teamContainer.setOnClickListener {
            val intent = Intent(context, TeamPageActivity::class.java)
            intent.putExtra(INTENT_KEY_TEAM_ID, team.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return this.teams.count()
    }
}