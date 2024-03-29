package com.codefrnd.mvvmapp.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.codefrnd.mvvmapp.R
import com.codefrnd.mvvmapp.data.db.entities.User
import com.codefrnd.mvvmapp.databinding.ActivityLoginBinding
import com.codefrnd.mvvmapp.ui.home.HomeActivity
import com.codefrnd.mvvmapp.uitl.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.net.ConnectException

class LoginActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]

        viewModel.getLoggedInUser().observe(this, Observer { user ->
            if (user != null) {
                Intent(this, HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        })

        binding.buttonSignIn.setOnClickListener {
            loginUser()
        }

        binding.textViewSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    private fun loginUser() {
        val email = binding.editTextEmail.text.toString().trim()
        val password = binding.editTextPassword.text.toString().trim()

        lifecycleScope.launch {
            try {
                val authResponse = viewModel.userLogin(email, password)

                if (authResponse.user != null) {
                    viewModel.savedLoggedInUser(authResponse.user)
                } else {
                    binding.root.snackbar(authResponse.message!!)
                }
            } catch (e: ApiException) {
                e.printStackTrace()
            } catch (e: ConnectException) {
                e.printStackTrace()
            } catch (e: NoInternetException) {
                e.printStackTrace()
            }
        }
    }
}