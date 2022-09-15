package com.iptvstreamer.weatherstation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iptvstreamer.weatherstation.library.controller.GetStationController
import com.iptvstreamer.weatherstation.library.controller.LoginController
import com.iptvstreamer.weatherstation.library.model.Device

class HomeViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>().apply {
        value = true
    }

    val isLoading: LiveData<Boolean> = _isLoading

    private val _devices = MutableLiveData<ArrayList<Device>>().apply {
        value = ArrayList()
    }

    val devices: LiveData<ArrayList<Device>> = _devices

    fun refreshData(accessToken: String) {
        if (_devices.value?.isEmpty() == true) {
            _isLoading.value = true
            GetStationController.getStationData(accessToken) { stationData ->
                _isLoading.value = false
                if (stationData != null) {
                    _devices.value = stationData.body.devices
                }
            }
        }

    }
}