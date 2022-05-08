package com.codefrnd.mvvmapp.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import com.codefrnd.mvvmapp.data.repository.UserRepository
import com.codefrnd.mvvmapp.uitl.ApiException
import com.codefrnd.mvvmapp.uitl.Coroutines
import com.codefrnd.mvvmapp.uitl.NoInternetException
import java.net.ConnectException

class AuthViewModel(
    private val repository: UserRepository
) : ViewModel() {

    var email: String? = null
    var password: String? = null

    var authListener: AuthListener? = null

    fun getLoggedInUser() = repository.getUser()

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
            } catch (e : NoInternetException) {
                authListener?.onFailure(e.message!!)
            }
        }
    }
}