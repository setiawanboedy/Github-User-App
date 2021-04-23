package com.attafakkur.githubuserapp.views.intentutil

import android.content.Context
import android.content.Intent
import com.attafakkur.githubuserapp.utils.Constants
import com.attafakkur.githubuserapp.views.activity.UserDetailActivity

class ToDetail {

    fun showUserDetail(context: Context?, username: String) {
        Intent(context, UserDetailActivity::class.java).also {
            it.putExtra(Constants.EXTRA_USER, username)
            context?.startActivity(it)
        }
    }
}