package com.codefrnd.mvvmapp.data.repository

import com.codefrnd.mvvmapp.data.network.MyApi
import com.codefrnd.mvvmapp.data.network.SafeApiRequest
import com.codefrnd.mvvmapp.data.network.response.AuthResponse
import retrofit2.Response

class UserRepository : SafeApiRequest() {
    suspend fun userLogin(email: String, password: String): AuthResponse {
        /*val loginResponse = MutableLiveData<String>()

        /** THIS IS A BAD PRACTICE USE DI FOR THIS */
        MyApi().userLogin(email, password)
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        loginResponse.value = response.body()?.string()
                    } else {
                        loginResponse.value = response.errorBody()?.string()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    loginResponse.value = t.message
                }

            })
        return loginResponse*/

        return apiRequest { MyApi().userLogin(email, password) }
        // return MyApi().userLogin(email, password)
    }
}