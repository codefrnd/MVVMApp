package com.codefrnd.mvvmapp.data.network.response

import com.codefrnd.mvvmapp.data.db.entities.User

data class AuthResponse(
    val isSuccessful: Boolean?,
    val message: String?,
    val user: User?
)