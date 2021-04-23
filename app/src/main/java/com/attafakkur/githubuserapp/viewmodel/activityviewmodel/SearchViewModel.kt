package com.attafakkur.githubuserapp.viewmodel.activityviewmodel

import android.app.Application
import android.content.Context
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.attafakkur.githubuserapp.R
import com.attafakkur.githubuserapp.data.network.UserRetrofitAPI
import com.attafakkur.githubuserapp.model.SearchModel
import com.attafakkur.githubuserapp.model.UserModel
import com.attafakkur.githubuserapp.utils.snackbar
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    private val searchUser: MutableLiveData<ArrayList<UserModel>> = MutableLiveData()
    private val resultSearch: MutableLiveData<SearchModel> = MutableLiveData()

    fun loadSearchUser(
        context: Context,
        view: View,
        username: String
    ) {
        viewModelScope.launch {
            UserRetrofitAPI.getRetrofitAPI().getUserSearch(username)
                .enqueue(object : retrofit2.Callback<SearchModel> {
                    override fun onResponse(
                        call: Call<SearchModel>,
                        response: Response<SearchModel>
                    ) {
                        if (response.isSuccessful) {
                            searchUser.postValue(response.body()?.itemUsers)
                            resultSearch.postValue(response.body())
                        }
                    }

                    override fun onFailure(call: Call<SearchModel>, t: Throwable) {
                        view.snackbar(context.getString(R.string.check_connection))
                    }
                })
        }
    }

    fun getSearchUsers(): LiveData<ArrayList<UserModel>> = searchUser
    fun getResultSearch(): LiveData<SearchModel> = resultSearch
}