package com.rodolfonavalon.nbateamviewer.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Player(
    @Json(name = "id")
    val id: Int,
    @Json(name = "first_name")
    val firstName: String,
    @Json(name = "last_name")
    val lastName: String,
    @Json(name = "position")
    val position: String,
    @Json(name = "number")
    val number: Int
)
