package com.rodolfonavalon.nbateamviewer.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rodolfonavalon.nbateamviewer.R
import com.rodolfonavalon.nbateamviewer.model.Team

class TeamAdapter: RecyclerView.Adapter<TeamViewHolder>()
{
    private val teams = mutableListOf<Team>()

    fun addAll(teams: List<Team>) {
        this.teams.clear()
        this.teams.addAll(teams)
        notifyDataSetChanged()
    }

    fun clear() {
        this.teams.clear()
        notifyDataSetChanged()
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
        holder.teamContainer.setOnClickListener {  }
    }

    override fun getItemCount(): Int {
        return this.teams.count()
    }
}