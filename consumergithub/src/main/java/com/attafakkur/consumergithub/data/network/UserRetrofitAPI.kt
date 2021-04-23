package com.attafakkur.consumergithub.data.network

import com.attafakkur.githubuserapp.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserRetrofitAPI {

    companion object ObjFactory {
        fun getRetrofitAPI(): InterfaceUserAPI {

            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(InterfaceUserAPI::class.java)

        }
    }

}