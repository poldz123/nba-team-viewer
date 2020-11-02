package com.rodolfonavalon.nbateamviewer.view.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rodolfonavalon.nbateamviewer.R

class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val teamContainer: ViewGroup = view.findViewById(R.id.container_team)
    val teamName: TextView = view.findViewById(R.id.textview_team_name)
    val teamWins: TextView = view.findViewById(R.id.textview_team_wins)
    val teamLosses: TextView = view.findViewById(R.id.textview_team_losses)
}
