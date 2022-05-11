package com.codefrnd.mvvmapp.ui.auth

import androidx.lifecycle.ViewModel
import com.codefrnd.mvvmapp.data.db.entities.User
import com.codefrnd.mvvmapp.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthViewModel(
    private val repository: UserRepository
) : ViewModel() {

    fun getLoggedInUser() = repository.getUser()

    suspend fun userLogin(
        email: String,
        password: String
    ) = withContext(Dispatchers.IO) { repository.userLogin(email, password) }

    suspend fun savedLoggedInUser(user: User) = repository.saveUser(user)

    suspend fun userSignUp(
        name: String,
        email: String,
        password: String
    ) = withContext(Dispatchers.IO) { repository.userSignUp(name, email, password) }
}