package com.attafakkur.consumergithub.view.activity

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.attafakkur.consumergithub.adapter.UserAdapter
import com.attafakkur.consumergithub.databinding.ActivityFavoriteBinding
import com.attafakkur.consumergithub.model.UserModel
import com.attafakkur.consumergithub.view.intentutil.ToDetail
import java.util.*


class FavoriteActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityFavoriteBinding

    private val favList = ArrayList<UserModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backFav.setOnClickListener(this)

        getUserFavoriteProvider()

    }

    private fun getUserFavoriteProvider() {
        val table = "favorite"
        val authority = "com.attafakkur.githubuserapp"

        val uri = Uri.Builder()
            .scheme("content")
            .authority(authority)
            .appendPath(table)
            .build()

        val contentResolver = this.contentResolver
        val cursor = contentResolver.query(uri, null, null, null, null)

        if (cursor != null && cursor.count > 0) {

            showRecyclerFavorite(cursor)
        }
    }

    private fun showRecyclerFavorite(cursor: Cursor) {
        binding.recyclerFavorite.adapter =
            UserAdapter(convertUser(cursor)) { user ->
                user as UserModel
                user.username?.let { ToDetail().showUserDetail(application, it) }
            }
        binding.recyclerFavorite.layoutManager =
            LinearLayoutManager(application)
    }

    private fun convertUser(cursor: Cursor): ArrayList<UserModel> {

        cursor.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow("id"))
                val avatar = getString(getColumnIndexOrThrow("avatar_url"))
                val username = getString(getColumnIndexOrThrow("username"))
                val type = getString(getColumnIndexOrThrow("type"))
                favList.add(
                    UserModel(
                        id,
                        username,
                        avatar,
                        type
                    )
                )
            }
        }
        return favList
    }

    override fun onClick(v: View?) {
        finish()
    }
}