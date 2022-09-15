package com.iptvstreamer.weatherstation.library.controller

import com.iptvstreamer.weatherstation.library.api.NetatmoAPI
import com.iptvstreamer.weatherstation.library.model.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginController {
    companion object {
        private const val CLIENT_ID = "5de0f2b5c5bdbd3e1c03c32d"
        private const val CLIENT_SECRET = "0cNAX50BAFSfLWpgkx3vKojGrJhtw"

        fun login(username: String, password: String, callback: (LoginResponse?) -> Unit) {
            val apiCall = NetatmoAPI.create().login(
                username, password, "password",
                CLIENT_ID, CLIENT_SECRET
            )
            apiCall.enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful) {
                        callback(response.body())
                    } else {
                        callback(null)
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    callback(null)
                }

            })
        }

        fun refreshToken(refreshToken: String, callback: (LoginResponse?) -> Unit) {
            val apiCall = NetatmoAPI.create().refreshToken(
                "refresh_token", refreshToken, CLIENT_ID,
                CLIENT_SECRET
            )
            apiCall.enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful) {
                        callback(response.body())
                    } else {
                        callback(null)
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    callback(null)
                }

            })
        }
    }
}