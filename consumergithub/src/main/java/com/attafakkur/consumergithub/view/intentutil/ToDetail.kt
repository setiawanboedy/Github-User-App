package com.attafakkur.consumergithub.view.intentutil

import android.content.Context
import android.content.Intent
import com.attafakkur.consumergithub.view.activity.UserDetailActivity
import com.attafakkur.githubuserapp.utils.Constants

class ToDetail {

    fun showUserDetail(context: Context?, username: String) {
        Intent(context, UserDetailActivity::class.java).also {
            it.putExtra(Constants.EXTRA_USER, username)
            context?.startActivity(it)
        }
    }
}