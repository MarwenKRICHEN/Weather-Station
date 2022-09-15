package com.iptvstreamer.weatherstation.listeners

import com.iptvstreamer.weatherstation.library.model.Module

interface DeviceAdapterListener {
    fun onModuleClick(module: Module)
}