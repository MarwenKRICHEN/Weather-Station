package com.iptvstreamer.weatherstation.library.controller

import com.iptvstreamer.weatherstation.library.api.NetatmoAPI
import com.iptvstreamer.weatherstation.library.model.StationData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetStationController {
    companion object {
        fun getStationData(accessToken: String, callback: (StationData?) -> Unit) {
            val apiCall = NetatmoAPI.create().getStationData("Bearer $accessToken")
            apiCall.enqueue(object : Callback<StationData> {
                override fun onResponse(
                    call: Call<StationData>,
                    response: Response<StationData>
                ) {
                    if (response.isSuccessful) {
                        callback(response.body())
                    } else {
                        callback(null)
                    }
                }

                override fun onFailure(call: Call<StationData>, t: Throwable) {
                    callback(null)
                }

            })
        }
    }
}