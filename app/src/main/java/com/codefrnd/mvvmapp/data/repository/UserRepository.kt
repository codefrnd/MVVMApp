package com.codefrnd.mvvmapp.data.repository

import com.codefrnd.mvvmapp.data.db.AppDB
import com.codefrnd.mvvmapp.data.db.entities.User
import com.codefrnd.mvvmapp.data.network.MyApi
import com.codefrnd.mvvmapp.data.network.SafeApiRequest
import com.codefrnd.mvvmapp.data.network.response.AuthResponse
import retrofit2.Response

class UserRepository(
    private val api: MyApi,
    private val db: AppDB
) : SafeApiRequest() {
    suspend fun userLogin(email: String, password: String): AuthResponse {
        return apiRequest { api.userLogin(email, password) }
    }

    suspend fun saveUser(user: User) = db.getUserDao().upsert(user)

    fun getUser() = db.getUserDao().getUser()

    suspend fun userSignUp(name: String, email: String, password: String): AuthResponse {
        return apiRequest { api.userSignUp(name, email, password) }
    }
}