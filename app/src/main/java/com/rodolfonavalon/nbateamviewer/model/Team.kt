package com.rodolfonavalon.nbateamviewer.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Team(
    @Json(name = "id")
    var id: Int,
    @Json(name = "wins")
    var wins: Int,
    @Json(name = "losses")
    var losses: Int,
    @Json(name = "full_name")
    var fullName: String,
    @Json(name = "players")
    var players: List<Player>
)