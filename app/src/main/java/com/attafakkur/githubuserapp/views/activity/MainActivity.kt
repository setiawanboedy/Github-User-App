package com.attafakkur.githubuserapp.views.activity

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.attafakkur.githubuserapp.R
import com.attafakkur.githubuserapp.databinding.ActivityMainBinding
import com.attafakkur.githubuserapp.utils.Constants
import com.attafakkur.githubuserapp.views.fragment.HomeFragment
import com.attafakkur.githubuserapp.views.fragment.SearchFragment
import com.attafakkur.githubuserapp.views.intentutil.ToDetail

class MainActivity : AppCompatActivity(), View.OnClickListener, SearchView.OnQueryTextListener {
    private lateinit var binding: ActivityMainBinding
    private val mHomeFragment = HomeFragment()
    private val mFragmentManager = supportFragmentManager
    private val mSearchFragment = SearchFragment()
    private val mBundle = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.account.setOnClickListener(this)
        binding.setting.setOnClickListener(this)
        binding.favButton.setOnClickListener(this)
        binding.homeUser.setOnClickListener(this)
        binding.alarmUser.setOnClickListener(this)

        val fragment = mFragmentManager
                .findFragmentByTag(HomeFragment::class.java.simpleName)
        if (fragment !is HomeFragment) {
            mFragmentManager.beginTransaction().apply {
                replace(R.id.frame_container, mHomeFragment, HomeFragment::class.java.simpleName)
                disallowAddToBackStack()
                commit()
            }
        }

        binding.searchUser.setOnQueryTextListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fav_button -> {
                startActivity(Intent(this, FavoriteActivity::class.java))
            }
            R.id.setting -> {
                Intent(Settings.ACTION_LOCALE_SETTINGS).also {
                    startActivity(it)
                }
            }
            R.id.account -> {
                ToDetail().showUserDetail(this, username = "setiawanboedy")
            }
            R.id.home_user -> {
                val mFragmentManager = supportFragmentManager
                val homeFragment = HomeFragment()
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container, homeFragment, HomeFragment::class.java.simpleName)
                    disallowAddToBackStack()
                    commit()
                }
            }
            R.id.alarm_user -> {
                startActivity(Intent(this, AlarmActivity::class.java))
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        val fragment = mFragmentManager
                .findFragmentByTag(HomeFragment::class.java.simpleName)
        if (fragment !is SearchFragment) {
            mBundle.putString(Constants.EXTRA_USER, query)
            mSearchFragment.arguments = mBundle
            mFragmentManager.beginTransaction().apply {
                replace(
                    R.id.frame_container,
                    mSearchFragment,
                    SearchFragment::class.java.simpleName
                )
                recreate()
                addToBackStack(null)
                commit()
            }
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }
}

