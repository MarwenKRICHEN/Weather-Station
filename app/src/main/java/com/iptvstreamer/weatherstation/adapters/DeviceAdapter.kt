package com.iptvstreamer.weatherstation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iptvstreamer.weatherstation.R
import com.iptvstreamer.weatherstation.library.model.Device
import com.iptvstreamer.weatherstation.listeners.DeviceAdapterListener

class DeviceAdapter(
    private val devices: ArrayList<Device>,
    private val context: Context?,
    private val listener: DeviceAdapterListener
) :
    RecyclerView.Adapter<DeviceAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_device, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val device = devices[position]
        holder.deviceName?.text = context?.getString(R.string.device_name, position + 1)
        holder.temperature?.text = context?.getString(
            R.string.device_temperature,
            device.dashboard_data["Temperature"] ?: "",
            "C"
        )
        holder.humidity?.text = context?.getString(
            R.string.device_humidity,
            device.dashboard_data["Humidity"] ?: "",
            "%"
        )
        holder.co2?.text =
            context?.getString(R.string.device_co2, device.dashboard_data["CO2"] ?: "", "ppm")
        holder.noise?.text =
            context?.getString(R.string.device_noise, device.dashboard_data["Noise"] ?: "", "dp")
        for (module in device.modules) {
            val inflater = LayoutInflater.from(context);
            val inflatedLayout =
                inflater.inflate(R.layout.layout_module_button, holder.modulesContainer, false)
            val button = inflatedLayout.findViewById<Button>(R.id.button)
            button.text = module.module_name
            holder.modulesContainer?.addView(inflatedLayout)
            button.setOnClickListener {
                listener.onModuleClick(module)
            }
        }
    }

    override fun getItemCount(): Int {
        return devices.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val deviceName: TextView? = itemView.findViewById(R.id.deviceName)
        val temperature: TextView? = itemView.findViewById(R.id.temperature)
        val humidity: TextView? = itemView.findViewById(R.id.humidity)
        val co2: TextView? = itemView.findViewById(R.id.co2)
        val noise: TextView? = itemView.findViewById(R.id.noise)
        val modulesContainer: LinearLayout? = itemView.findViewById(R.id.modules)
    }

    fun updateData(devices: ArrayList<Device>) {
        this.devices.clear()
        this.devices.addAll(devices)
        notifyDataSetChanged()
    }
}