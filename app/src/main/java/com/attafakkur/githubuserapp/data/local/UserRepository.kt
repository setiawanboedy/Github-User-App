package com.attafakkur.githubuserapp.data.local

import androidx.lifecycle.LiveData
import com.attafakkur.githubuserapp.model.DetailModel
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {

    val readAllUser: LiveData<List<DetailModel>> = userDao.readAllUser()

    suspend fun addUser(user: DetailModel) {
        userDao.addUser(user)
    }

    suspend fun deleteUser(user: DetailModel) {
        userDao.deleteUser(user)
    }

    suspend fun deleteAllUser() {
        userDao.deleteAllUser()
    }

    suspend fun existID(id: Int?): Boolean {
        return userDao.exists(id)
    }

    fun searchFavoriteUser(searchQuery: String): Flow<List<DetailModel>> {
        return userDao.searchFavoriteUser(searchQuery)
    }

}