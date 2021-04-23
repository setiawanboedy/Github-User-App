package com.attafakkur.githubuserapp.viewmodel.fragmentviewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.attafakkur.githubuserapp.data.network.UserRetrofitAPI
import com.attafakkur.githubuserapp.model.UserModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class FollowerViewModel(application: Application) : AndroidViewModel(application) {
    private val allFollowers: MutableLiveData<ArrayList<UserModel>> = MutableLiveData()

    fun loadAllFollowers(username: String?) {
        viewModelScope.launch {
            UserRetrofitAPI.getRetrofitAPI().getUserFollowers(username)
                .enqueue(object : retrofit2.Callback<ArrayList<UserModel>> {
                    override fun onResponse(
                        call: Call<ArrayList<UserModel>>,
                        response: Response<ArrayList<UserModel>>
                    ) {
                        if (response.isSuccessful) {
                            allFollowers.value = response.body()
                        }
                    }

                    override fun onFailure(call: Call<ArrayList<UserModel>>, t: Throwable) {
                        return
                    }

                })
        }
    }

    fun getAllFollowers(): MutableLiveData<ArrayList<UserModel>> = allFollowers
}