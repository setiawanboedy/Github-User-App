package com.attafakkur.consumergithub.viewmodel.activity

import android.app.Application
import android.content.Context
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.attafakkur.consumergithub.R
import com.attafakkur.consumergithub.data.network.UserRetrofitAPI
import com.attafakkur.consumergithub.model.DetailModel
import com.attafakkur.githubuserapp.utils.snackbar
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val detailUser: MutableLiveData<DetailModel> = MutableLiveData()

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

}