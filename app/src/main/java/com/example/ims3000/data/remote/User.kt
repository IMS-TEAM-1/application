package com.example.ims3000.data.remote

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("created_at") val created_at: String,
    @SerializedName("id") val id: Int,
    @SerializedName("password") val password: String,
    @SerializedName("token") val token: Any,
    @SerializedName("updated_at") val updated_at: String,
    @SerializedName("username") val username: String
)