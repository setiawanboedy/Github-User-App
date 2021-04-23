package com.attafakkur.githubuserapp.utils

import android.view.View
import android.widget.ProgressBar
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

fun ProgressBar.show() {
    visibility = View.VISIBLE
}

fun ProgressBar.hide() {
    visibility = View.GONE
}

fun refresh(swipeRefreshLayout: SwipeRefreshLayout, listener: (Any) -> Unit) {
    swipeRefreshLayout.setOnRefreshListener {
        listener.also(listener)

        swipeRefreshLayout.isRefreshing = false
    }
}

fun View.snackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).also { snackbar ->
        snackbar.setAction("OK") {
            snackbar.dismiss()
        }
    }.show()
}

val setDate: String = SimpleDateFormat("d MMM yyyy").format(Date())

fun View.show() {
    visibility = View.VISIBLE
}


