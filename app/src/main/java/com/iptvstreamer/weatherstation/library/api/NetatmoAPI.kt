package com.iptvstreamer.weatherstation.library.api

import com.iptvstreamer.weatherstation.library.model.LoginResponse
import com.iptvstreamer.weatherstation.library.model.StationData
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface NetatmoAPI {
    companion object {
        var BASE_URL = "https://api.netatmo.com/"

        fun create(): NetatmoAPI {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(NetatmoAPI::class.java)

        }
    }

    @FormUrlEncoded
    @POST("oauth2/token")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("grant_type") grant_type: String,
        @Field("client_id") client_id: String,
        @Field("client_secret") client_secret: String,
    ): Call<LoginResponse>

    @GET("api/getstationsdata")
    fun getStationData(
       @Header("Authorization") accessToken: String
    ): Call<StationData>

    @FormUrlEncoded
    @POST("oauth2/token")
    fun refreshToken(
        @Field("grant_type") grant_type: String,
        @Field("refresh_token") refresh_token: String,
        @Field("client_id") client_id: String,
        @Field("client_secret") client_secret: String,
    ): Call<LoginResponse>
}