package com.attafakkur.consumergithub.model


import com.google.gson.annotations.SerializedName

data class UserModel(
    @field:SerializedName("id")
    val id: Int?,
    @field:SerializedName("login")
    val username: String?,
    @field:SerializedName("avatar_url")
    val avatar_url: String?,
    @field:SerializedName("type")
    val type: String?
)
