package com.codefrnd.mvvmapp.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import com.codefrnd.mvvmapp.data.repository.UserRepository
import com.codefrnd.mvvmapp.uitl.ApiException
import com.codefrnd.mvvmapp.uitl.Coroutines
import java.net.ConnectException

class AuthViewModel : ViewModel() {

    var email: String? = null
    var password: String? = null

    var authListener: AuthListener? = null

    fun onLoginButtonClick(view: View) {
        authListener?.onStarted()

        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Invalid Password Or Email")

            return
        }

        /** BAD PRACTICE
         * WE SHOULD NOT CREATE INSTANCE OTH OTHER CLASSES RESULTING IN TIGHT COUPLING
         * USE DI
         * */

        /*val loginResponse = UserRepository().userLogin(email!!, password!!)
        authListener?.onSuccess(loginResponse)*/

        Coroutines.main {
            try {
                val authResponse = UserRepository().userLogin(email!!, password!!)
                authResponse.user?.let {
                    authListener?.onSuccess(it)
                    return@main
                }
                authListener?.onFailure(authResponse.message!!)
            } catch (e: ApiException) {
                authListener?.onFailure(e.message!!)
            } catch (e: ConnectException) {
                authListener?.onFailure(e.message!!)
            }
        }
    }
}