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

class FollowingViewModel(application: Application) : AndroidViewModel(application) {
    private val allFollowing: MutableLiveData<ArrayList<UserModel>> = MutableLiveData()

    fun loadAllFollowing(username: String?) {

        viewModelScope.launch {
            UserRetrofitAPI.getRetrofitAPI().getUserFollowing(username)
                .enqueue(object : retrofit2.Callback<ArrayList<UserModel>> {
                    override fun onResponse(
                        call: Call<ArrayList<UserModel>>,
                        response: Response<ArrayList<UserModel>>
                    ) {
                        if (response.isSuccessful) {
                            allFollowing.value = response.body()
                        }
                    }

                    override fun onFailure(call: Call<ArrayList<UserModel>>, t: Throwable) {
                        return
                    }
                })
        }
    }

    fun getAllFollowing(): MutableLiveData<ArrayList<UserModel>> = allFollowing
}