package com.attafakkur.githubuserapp.data.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.attafakkur.githubuserapp.data.local.UserDao
import com.attafakkur.githubuserapp.data.local.UserDatabase
import kotlinx.coroutines.InternalCoroutinesApi

class ContentProvider : ContentProvider() {

    companion object {
        private const val USER = 1
        private const val AUTHORITY = "com.attafakkur.githubuserapp"
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, "favorite", USER)
        }
    }

    @InternalCoroutinesApi
    private val userDao: UserDao by lazy {
        UserDatabase.getDatabase(requireNotNull(context)).userDao()
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        throw UnsupportedOperationException()
    }

    override fun getType(uri: Uri): String? {
        throw UnsupportedOperationException()
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw UnsupportedOperationException()
    }

    override fun onCreate(): Boolean {
        return true
    }

    @InternalCoroutinesApi
    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when (uriMatcher.match(uri)) {
            USER -> userDao.cursorGetAllUser()
            else -> null
        }
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        throw UnsupportedOperationException()
    }

}