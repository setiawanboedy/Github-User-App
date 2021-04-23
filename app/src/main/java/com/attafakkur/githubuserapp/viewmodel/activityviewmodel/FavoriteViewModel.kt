package com.attafakkur.githubuserapp.viewmodel.activityviewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.attafakkur.githubuserapp.data.local.UserDatabase
import com.attafakkur.githubuserapp.data.local.UserRepository
import com.attafakkur.githubuserapp.model.DetailModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val readAllUser: LiveData<List<DetailModel>>
    private val repository: UserRepository

    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
        readAllUser = repository.readAllUser
    }

    fun readAllUser() = readAllUser

    fun deleteAllUser() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllUser()
        }
    }

    fun searchFavoriteUser(searchQuery: String): LiveData<List<DetailModel>> {
        return repository.searchFavoriteUser(searchQuery).asLiveData()
    }
}