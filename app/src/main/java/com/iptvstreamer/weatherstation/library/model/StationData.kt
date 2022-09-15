package com.iptvstreamer.weatherstation.library.model

import java.io.Serializable

data class StationData(val body: StationBody)

data class StationBody(val devices: ArrayList<Device>)

data class Device(val _id: String, val dashboard_data: Map<String, Any>, val modules: ArrayList<Module>)

data class Module(
    val _id: String,
    val module_name: String,
    val type: String,
    val dashboard_data: Map<String, Any>?
): Serializable