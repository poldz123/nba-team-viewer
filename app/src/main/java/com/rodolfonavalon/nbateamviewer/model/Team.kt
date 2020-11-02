package com.rodolfonavalon.nbateamviewer.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Team(
    @Json(name = "id")
    val id: Int,
    @Json(name = "wins")
    val wins: Int,
    @Json(name = "losses")
    val losses: Int,
    @Json(name = "full_name")
    val fullName: String,
    @Json(name = "players")
    val players: List<Player>
)