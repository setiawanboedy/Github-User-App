package com.attafakkur.githubuserapp.views.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.attafakkur.githubuserapp.R
import com.attafakkur.githubuserapp.adapter.FavoriteAdapter
import com.attafakkur.githubuserapp.databinding.ActivityFavoriteBinding
import com.attafakkur.githubuserapp.model.DetailModel
import com.attafakkur.githubuserapp.viewmodel.activityviewmodel.FavoriteViewModel
import com.attafakkur.githubuserapp.views.intentutil.ToDetail
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class FavoriteActivity : AppCompatActivity(), View.OnClickListener, SearchView.OnQueryTextListener {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backFav.setOnClickListener(this)

        initLoadFavorite()

        binding.deleteAll.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle(resources.getString(R.string.title))
                .setMessage(resources.getString(R.string.message))
                .setNegativeButton(resources.getString(R.string.no)) { dialog, _ ->
                    dialog.dismiss()
                }
                .setPositiveButton(resources.getString(R.string.yes)) { _, _ ->
                    favoriteViewModel.deleteAllUser()
                }
                .show()

        }

        binding.searchFavorite.setOnQueryTextListener(this)
    }

    private fun initLoadFavorite() {
        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        favoriteViewModel.readAllUser().observe(this, { user ->
            binding.recyclerFavorite.adapter = FavoriteAdapter(user) { userAll ->
                userAll as DetailModel
                userAll.username?.let { ToDetail().showUserDetail(this, it) }
            }
        })
        binding.recyclerFavorite.layoutManager =
            LinearLayoutManager(this)
    }


    override fun onClick(v: View?) {
        finish()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        searchUser(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        searchUser(newText)
        return true
    }

    private fun searchUser(query: String?) {
        val searchQuery = "%$query%"

        favoriteViewModel.searchFavoriteUser(searchQuery).observe(this, { list ->
            list?.let {
                binding.recyclerFavorite.adapter = FavoriteAdapter(it) { user ->
                    user as DetailModel
                    user.username?.let { it1 -> ToDetail().showUserDetail(this, it1) }
                }
            }
        })
    }

}