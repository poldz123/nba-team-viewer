package com.rodolfonavalon.nbateamviewer.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rodolfonavalon.nbateamviewer.R
import com.rodolfonavalon.nbateamviewer.model.Player

class PlayerAdapter : RecyclerView.Adapter<PlayerViewHolder>() {
    private val players = mutableListOf<Player>()

    fun addAll(players: List<Player>) {
        this.players.clear()
        this.players.addAll(players)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_player, parent, false)
        return PlayerViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val player = players[position]
        val context = holder.itemView.context
        holder.playerName.text = "${player.firstName} ${player.lastName}"
        holder.playerPosition.text = context.getString(R.string.player_position, player.position)
        holder.playerNumber.text = context.getString(R.string.player_number, player.number)
    }

    override fun getItemCount(): Int {
        return this.players.count()
    }
}
