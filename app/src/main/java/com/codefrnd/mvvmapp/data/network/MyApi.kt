package com.codefrnd.mvvmapp.data.network

import com.codefrnd.mvvmapp.data.network.response.AuthResponse
import com.codefrnd.mvvmapp.data.network.response.QuotesResponse
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface MyApi {

    @FormUrlEncoded
    @POST(value = "login")
    suspend fun userLogin(
        @Field(value = "email") email: String,
        @Field(value = "password") password: String
    ): Response<AuthResponse>

    @FormUrlEncoded
    @POST(value = "signup")
    suspend fun userSignUp(
        @Field(value = "name") name: String,
        @Field(value = "email") email: String,
        @Field(value = "password") password: String
    ): Response<AuthResponse>

    @GET("quotes")
    suspend fun getQuotes(): Response<QuotesResponse>

    companion object {
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): MyApi {

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.simplifiedcoding.in/course-apis/mvvm/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }
}