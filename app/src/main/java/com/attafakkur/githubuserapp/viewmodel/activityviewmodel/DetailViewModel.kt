package com.attafakkur.githubuserapp.viewmodel.activityviewmodel

import android.app.Application
import android.content.Context
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.attafakkur.githubuserapp.R
import com.attafakkur.githubuserapp.data.local.UserDatabase
import com.attafakkur.githubuserapp.data.local.UserRepository
import com.attafakkur.githubuserapp.data.network.UserRetrofitAPI
import com.attafakkur.githubuserapp.model.DetailModel
import com.attafakkur.githubuserapp.utils.snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val detailUser: MutableLiveData<DetailModel>
    private val repository: UserRepository

    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        detailUser = MutableLiveData()
        repository = UserRepository(userDao)
    }

    fun loadDetailUser(
        context: Context,
        view: View,
        username: String
    ) {
        viewModelScope.launch {
            UserRetrofitAPI.getRetrofitAPI().getUserDetail(username)
                .enqueue(object : retrofit2.Callback<DetailModel> {
                    override fun onResponse(
                        call: Call<DetailModel>,
                        response: Response<DetailModel>
                    ) {
                        if (response.isSuccessful) {
                            detailUser.value = response.body()
                        }
                    }

                    override fun onFailure(call: Call<DetailModel>, t: Throwable) {
                        view.snackbar(context.getString(R.string.check_connection))
                    }

                })
        }
    }

    fun getDetailUser(): LiveData<DetailModel> = detailUser
    fun addUser(user: DetailModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }

    fun deleteUser(user: DetailModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUser(user)
        }
    }

    fun getExist(id: Int?): Boolean =
        runBlocking(Dispatchers.IO) {
            return@runBlocking repository.existID(id)
        }
}