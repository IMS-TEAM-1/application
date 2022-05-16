package com.example.ims3000.data.remote

import java.util.*

data class MowerImage(

    val id: Int,
    val mower_location_id: Int,
    val image: Base64,
    val classification: String

)
