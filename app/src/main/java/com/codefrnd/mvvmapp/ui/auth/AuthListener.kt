package com.codefrnd.mvvmapp.ui.auth

import androidx.lifecycle.LiveData
import com.codefrnd.mvvmapp.data.db.entities.User

interface AuthListener {
    fun onStarted()
    fun onSuccess(user: User)
    fun onFailure(msg: String)
}