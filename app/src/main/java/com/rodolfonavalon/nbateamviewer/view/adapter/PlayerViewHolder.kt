package com.rodolfonavalon.nbateamviewer.view.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rodolfonavalon.nbateamviewer.R

class PlayerViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val playerName: TextView = view.findViewById(R.id.textview_player_name)
    val playerPosition: TextView = view.findViewById(R.id.textview_player_position)
    val playerNumber: TextView = view.findViewById(R.id.textview_player_number)
}