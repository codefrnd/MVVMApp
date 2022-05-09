package com.codefrnd.mvvmapp.ui.home.profile

import androidx.lifecycle.ViewModel
import com.codefrnd.mvvmapp.data.repository.UserRepository

class ProfileViewModel(
    repository: UserRepository
) : ViewModel() {
    val user = repository.getUser()
}