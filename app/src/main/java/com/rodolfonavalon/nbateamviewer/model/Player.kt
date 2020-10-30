package com.rodolfonavalon.nbateamviewer.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Player(
    @Json(name = "id")
    var id: Int,
    @Json(name = "first_name")
    var firstName: String,
    @Json(name = "last_name")
    var lastName: String,
    @Json(name = "position")
    var position: String,
    @Json(name = "number")
    var number: Int
)