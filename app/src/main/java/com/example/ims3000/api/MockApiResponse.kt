package com.example.ims3000.api

import com.google.gson.annotations.SerializedName

data class MockApiResponse(
    @SerializedName("userId") val userId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("id") val id: Int,
    @SerializedName("completed") val completed: Boolean
)
