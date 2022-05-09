package com.codefrnd.mvvmapp.ui.auth

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.codefrnd.mvvmapp.data.db.entities.User
import com.codefrnd.mvvmapp.data.repository.UserRepository
import com.codefrnd.mvvmapp.uitl.ApiException
import com.codefrnd.mvvmapp.uitl.Coroutines
import com.codefrnd.mvvmapp.uitl.NoInternetException
import java.net.ConnectException

class AuthViewModel(
    private val repository: UserRepository
) : ViewModel() {

    var name: String? = null
    var email: String? = null
    var password: String? = null
    var passwordConfirm: String? = null

    var authListener: AuthListener? = null

    fun getLoggedInUser() = repository.getUser()

    fun onLoginButtonClick(view: View) {
        authListener?.onStarted()

        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Invalid Password Or Email")

            return
        }

        Coroutines.main {
            try {
                val authResponse = repository.userLogin(email!!, password!!)
                authResponse.user?.let {
                    authListener?.onSuccess(it)
                    repository.saveUser(it)
                    return@main
                }
                authListener?.onFailure(authResponse.message!!)
            } catch (e: ApiException) {
                authListener?.onFailure(e.message!!)
            } catch (e: ConnectException) {
                authListener?.onFailure(e.message!!)
            } catch (e: NoInternetException) {
                authListener?.onFailure(e.message!!)
            }
        }
    }

    fun onSignUpButtonClick(view: View) {
        authListener?.onStarted()

        if (name.isNullOrEmpty()) {
            authListener?.onFailure("Name is required.")
            return
        }

        if (email.isNullOrEmpty()) {
            authListener?.onFailure("Email is required.")
            return
        }

        if (password.isNullOrEmpty()) {
            authListener?.onFailure("Password is required.")
            return
        }

        if (password != passwordConfirm) {
            authListener?.onFailure("Password did not match.")
            return
        }

        val currTime = System.currentTimeMillis().toString()
        var demoUser = User(1, name, email, password, currTime, currTime, currTime)

        Coroutines.main {
            authListener?.onSuccess(demoUser)
            repository.saveUser(demoUser)
            return@main

            /*try {
                val authResponse = repository.userSignUp(name!!, email!!, password!!)
                authResponse.user?.let {
                    authListener?.onSuccess(it)
                    repository.saveUser(it)
                    return@main
                }
                authListener?.onFailure(authResponse.message!!)
            } catch (e: ApiException) {
                authListener?.onFailure(e.message!!)
            } catch (e: ConnectException) {
                authListener?.onFailure(e.message!!)
            } catch (e: NoInternetException) {
                authListener?.onFailure(e.message!!)
            }*/
        }
    }

    fun onSignUp(view: View) {
        Intent(view.context, SignUpActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun onLogin(view: View) {
        Intent(view.context, LoginActivity::class.java).also {
            view.context.startActivity(it)
        }
    }
}