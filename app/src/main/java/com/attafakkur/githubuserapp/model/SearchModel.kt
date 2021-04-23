package com.attafakkur.githubuserapp.model

import com.google.gson.annotations.SerializedName

data class SearchModel(
    @field:SerializedName("total_count")
    val totalResult: Int?,
    @field:SerializedName("items")
    val itemUsers: ArrayList<UserModel>
)