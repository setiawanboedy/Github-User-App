package com.attafakkur.githubuserapp.views.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.attafakkur.githubuserapp.R
import com.attafakkur.githubuserapp.adapter.FollowAdapter
import com.attafakkur.githubuserapp.databinding.ActivityUserDetailBinding
import com.attafakkur.githubuserapp.model.DetailModel
import com.attafakkur.githubuserapp.utils.*
import com.attafakkur.githubuserapp.viewmodel.activityviewmodel.DetailViewModel
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var favorite: DetailModel

    private lateinit var binding: ActivityUserDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backDetail.setOnClickListener(this)
        val username = intent.getStringExtra(Constants.EXTRA_USER)

        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        initLoadDetail(detailViewModel, username)
        tabManage()

        binding.starAdd.setOnClickListener {
            if (detailViewModel.getExist(favorite.id)) {
                deleteUser(favorite)

            } else {
                addFavorite(favorite)
            }
        }
    }

    private fun initLoadDetail(detailViewModel: DetailViewModel, username: String?) {

        binding.pbDetail.show()

        if (username != null) {
            detailViewModel.loadDetailUser(
                applicationContext,
                binding.pbDetail,
                username
            )
        }
        detailViewModel.getDetailUser().observe(this, {
            Glide.with(applicationContext)
                .load(it.avatar_url)
                .centerCrop()
                .into(binding.imageDetail)
            binding.userDetail.text = username
            binding.nameDetail.text = it.name
            binding.locationDetail.text = it.location ?: getString(R.string.unknown)
            binding.company.text = it.company ?: getString(R.string.unknown)
            binding.repository.text = it.repository.toString()
            binding.follower.text = it.followers.toString()
            binding.following.text = it.following.toString()

            binding.pbDetail.hide()

            if (it != null) {
                favorite = DetailModel(
                    it.id,
                    it.name,
                    it.username,
                    it.avatar_url,
                    it.company,
                    it.location,
                    it.repository,
                    it.type,
                    it.followers,
                    it.following
                )
            }
            isFavorite(favorite)
        })
    }

    private fun addFavorite(user: DetailModel) {
        detailViewModel.addUser(user)
        binding.starAdd.setImageResource(R.drawable.ic_start_fill)
        binding.rootDetail.snackbar("${user.name} Added to Favorite")
    }

    private fun deleteUser(user: DetailModel) {
        detailViewModel.deleteUser(user)
        binding.starAdd.setImageResource(R.drawable.ic_star_outline)
        binding.rootDetail.snackbar("${user.name} Deleted from Favorite")
    }

    private fun isFavorite(user: DetailModel) {
        if (detailViewModel.getExist(user.id)) {
            binding.starAdd.setImageResource(R.drawable.ic_start_fill)
        } else {
            binding.starAdd.setImageResource(R.drawable.ic_star_outline)
        }
    }

    private fun tabManage() {
        val sectionsPagerAdapter = FollowAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TabTitles.TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    override fun onClick(v: View?) {
        finish()
    }
}