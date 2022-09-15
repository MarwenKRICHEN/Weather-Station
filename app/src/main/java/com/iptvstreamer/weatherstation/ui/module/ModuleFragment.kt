package com.iptvstreamer.weatherstation.ui.module

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.iptvstreamer.weatherstation.R
import com.iptvstreamer.weatherstation.databinding.FragmentModuleBinding

class ModuleFragment : Fragment() {

    private var _binding: FragmentModuleBinding? = null
    private val args: ModuleFragmentArgs by navArgs()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentModuleBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.moduleName.text = args.module.module_name
        binding.moduleType.text = getString(R.string.module_type, args.module.type)
        binding.temperature.text =
            getString(R.string.device_temperature, args.module.dashboard_data?.get("Temperature") ?: "", "C")
        binding.minTemp.text =
            getString(R.string.module_min_temp, args.module.dashboard_data?.get("min_temp") ?: "", "C")
        binding.maxTemp.text =
            getString(R.string.module_max_temp, args.module.dashboard_data?.get("max_temp") ?: "", "C")
        binding.humidity.text =
            getString(R.string.device_humidity, args.module.dashboard_data?.get("Humidity") ?: "", "%")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}