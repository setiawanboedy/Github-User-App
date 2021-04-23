package com.attafakkur.consumergithub.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "favorite")
data class DetailModel(
    @PrimaryKey(autoGenerate = false)
    @field:SerializedName("id")
    @ColumnInfo(name = "id") var id: Int?,
    @field:SerializedName("name")
    val name: String?,
    @field:SerializedName("login")
    val username: String?,
    @field:SerializedName("avatar_url")
    val avatar_url: String?,
    @field:SerializedName("company")
    val company: String?,
    @field:SerializedName("location")
    val location: String?,
    @field:SerializedName("public_repos")
    val repository: Int?,
    @field:SerializedName("type")
    val type: String?,
    @field:SerializedName("followers")
    val followers: Int?,
    @field:SerializedName("following")
    val following: Int?
)
//@PrimaryKey(autoGenerate = false)
//@field:SerializedName("id")
//val id: Int?,
//@field:SerializedName("name")
//@ColumnInfo(name = "name") val name: String?,
//@field:SerializedName("login")
//@ColumnInfo(name = "login") val username: String?,
//@field:SerializedName("avatar_url")
//@ColumnInfo(name = "avatar_url") val avatar_url: String?,
//@field:SerializedName("company")
//@ColumnInfo(name = "company") val company: String?,
//@field:SerializedName("location")
//@ColumnInfo(name = "location") val location: String?,
//@field:SerializedName("public_repos")
//@ColumnInfo(name = "repository") val repository: Int?,
//@field:SerializedName("followers")
//@ColumnInfo(name = "followers") val followers: Int?,
//@field:SerializedName("following")
//@ColumnInfo(name = "following") val following: Int?