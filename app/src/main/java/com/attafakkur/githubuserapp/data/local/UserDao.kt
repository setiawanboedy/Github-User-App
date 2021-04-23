package com.attafakkur.githubuserapp.data.local

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import com.attafakkur.githubuserapp.model.DetailModel
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: DetailModel)

    @Query("SELECT * FROM favorite")
    fun readAllUser(): LiveData<List<DetailModel>>

    @Delete
    suspend fun deleteUser(user: DetailModel)

    @Query("DELETE FROM favorite")
    suspend fun deleteAllUser()

    @Query("SELECT EXISTS (SELECT 1 FROM favorite WHERE id = :id)")
    suspend fun exists(id: Int?): Boolean

    @Query("SELECT * FROM favorite")
    fun cursorGetAllUser(): Cursor

    @Query("SELECT * FROM favorite WHERE username LIKE :searchQuery")
    fun searchFavoriteUser(searchQuery: String): Flow<List<DetailModel>>
}