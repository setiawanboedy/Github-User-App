package com.attafakkur.githubuserapp.viewmodel.activityviewmodel

import android.app.Application
import android.content.Context
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.attafakkur.githubuserapp.R
import com.attafakkur.githubuserapp.data.network.UserRetrofitAPI
import com.attafakkur.githubuserapp.model.UserModel
import com.attafakkur.githubuserapp.utils.snackbar
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class UsersViewModel(application: Application) : AndroidViewModel(application) {

    private val allUsers: MutableLiveData<ArrayList<UserModel>> = MutableLiveData()

    fun loadAllUsers(context: Context, view: View) {
        viewModelScope.launch {
            UserRetrofitAPI.getRetrofitAPI().getUsersAll()
                .enqueue(object : retrofit2.Callback<ArrayList<UserModel>> {
                    override fun onResponse(
                        call: Call<ArrayList<UserModel>>,
                        response: Response<ArrayList<UserModel>>
                    ) {
                        if (response.isSuccessful) {
                            allUsers.value = response.body()
                        }
                    }

                    override fun onFailure(call: Call<ArrayList<UserModel>>, t: Throwable) {
                        view.snackbar(context.getString(R.string.check_connection))
                    }

                })
        }
    }

    fun getAllUsers(): MutableLiveData<ArrayList<UserModel>> = allUsers
}