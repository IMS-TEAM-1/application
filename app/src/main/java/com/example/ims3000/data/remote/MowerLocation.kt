package com.example.ims3000.data.remote

data class MowerLocation(

    val id: Int,
    val mower_id: Int,
    val x: Float,
    val y: Float,
    val created_at: String,
    val images: List<MowerImage>

)
