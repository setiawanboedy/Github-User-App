package com.attafakkur.consumergithub.view.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.attafakkur.consumergithub.R
import com.attafakkur.consumergithub.adapter.FollowAdapter
import com.attafakkur.consumergithub.databinding.ActivityUserDetailBinding
import com.attafakkur.consumergithub.viewmodel.activity.DetailViewModel
import com.attafakkur.githubuserapp.utils.Constants
import com.attafakkur.githubuserapp.utils.TabTitles
import com.attafakkur.githubuserapp.utils.hide
import com.attafakkur.githubuserapp.utils.show
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var detailViewModel: DetailViewModel

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
        })
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