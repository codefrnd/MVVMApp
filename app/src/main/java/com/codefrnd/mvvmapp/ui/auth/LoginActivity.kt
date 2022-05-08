package com.codefrnd.mvvmapp.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.codefrnd.mvvmapp.R
import com.codefrnd.mvvmapp.data.db.AppDB
import com.codefrnd.mvvmapp.data.db.entities.User
import com.codefrnd.mvvmapp.data.network.MyApi
import com.codefrnd.mvvmapp.data.repository.UserRepository
import com.codefrnd.mvvmapp.databinding.ActivityLoginBinding
import com.codefrnd.mvvmapp.ui.home.HomeActivity
import com.codefrnd.mvvmapp.uitl.hide
import com.codefrnd.mvvmapp.uitl.show
import com.codefrnd.mvvmapp.uitl.snackbar
import com.codefrnd.mvvmapp.uitl.toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), AuthListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val api = MyApi()
        val db = AppDB(this)
        val repository = UserRepository(api, db)
        val factory = AuthViewModelFactory(repository)

        val binding: ActivityLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)
        val viewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]
        binding.viewModel = viewModel
        viewModel.authListener = this

        viewModel.getLoggedInUser().observe(this, Observer { user ->
            if (user != null) {
                Intent(this , HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        })
    }

    override fun onStarted() {
        progress_bar.show()
    }

    override fun onSuccess(user: User) {
        progress_bar.hide()
    }

    override fun onFailure(msg: String) {
        progress_bar.hide()
        root_layout.snackbar(msg)
    }
}