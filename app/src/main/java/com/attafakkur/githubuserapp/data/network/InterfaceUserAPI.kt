package com.attafakkur.githubuserapp.data.network

import com.attafakkur.githubuserapp.model.DetailModel
import com.attafakkur.githubuserapp.model.SearchModel
import com.attafakkur.githubuserapp.model.UserModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface InterfaceUserAPI {

    @Headers("Authorization: \${BuildConfig.TOKEN}")
    @GET("users?")
    fun getUsersAll(
    ): Call<ArrayList<UserModel>>

    @Headers("Authorization: \${BuildConfig.TOKEN}")
    @GET("search/users?")
    fun getUserSearch(
        @Query("q") searchUser: String?
    ): Call<SearchModel>

    @Headers("Authorization: \${BuildConfig.TOKEN}")
    @GET("users/{username}")
    fun getUserDetail(
        @Path("username") username: String?
    ): Call<DetailModel>

    @Headers("Authorization: \${BuildConfig.TOKEN}")
    @GET("users/{username}/followers")
    fun getUserFollowers(
        @Path("username") username: String?
    ): Call<ArrayList<UserModel>>

    @Headers("Authorization: \${BuildConfig.TOKEN}")
    @GET("users/{username}/following")
    fun getUserFollowing(
        @Path("username") username: String?
    ): Call<ArrayList<UserModel>>
}