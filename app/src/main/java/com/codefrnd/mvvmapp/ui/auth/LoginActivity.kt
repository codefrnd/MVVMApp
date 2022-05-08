package com.codefrnd.mvvmapp.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.codefrnd.mvvmapp.R
import com.codefrnd.mvvmapp.data.db.entities.User
import com.codefrnd.mvvmapp.databinding.ActivityLoginBinding
import com.codefrnd.mvvmapp.uitl.hide
import com.codefrnd.mvvmapp.uitl.show
import com.codefrnd.mvvmapp.uitl.snackbar
import com.codefrnd.mvvmapp.uitl.toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), AuthListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)
        val viewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        binding.viewModel = viewModel
        viewModel.authListener = this
    }

    override fun onStarted() {
        progress_bar.show()
    }

    override fun onSuccess(user: User) {
        progress_bar.hide()
        root_layout.snackbar("${user.name} is Logged In")
    }

    override fun onFailure(msg: String) {
        progress_bar.hide()
        root_layout.snackbar(msg)
    }
}