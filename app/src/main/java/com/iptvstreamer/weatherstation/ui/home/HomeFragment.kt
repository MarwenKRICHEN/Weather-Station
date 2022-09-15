package com.iptvstreamer.weatherstation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.iptvstreamer.weatherstation.adapters.DeviceAdapter
import com.iptvstreamer.weatherstation.databinding.FragmentHomeBinding
import com.iptvstreamer.weatherstation.library.model.Module
import com.iptvstreamer.weatherstation.listeners.DeviceAdapterListener

class HomeFragment : Fragment(), DeviceAdapterListener {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var deviceAdapter: DeviceAdapter
    private val args: HomeFragmentArgs by navArgs()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.itemAnimator = DefaultItemAnimator()
        deviceAdapter = DeviceAdapter(ArrayList(), context, this)
        binding.recyclerView.adapter = deviceAdapter

        homeViewModel.refreshData(args.accessToken)

        homeViewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            if (isLoading)
                binding.progressBar.visibility = View.VISIBLE
            else
                binding.progressBar.visibility = View.GONE
        })

        homeViewModel.devices.observe(viewLifecycleOwner, Observer { devices ->
            deviceAdapter.updateData(devices)
        })

    }

    override fun onModuleClick(module: Module) {
        val action = HomeFragmentDirections.actionFirstFragmentToSecondFragment(module, module.module_name)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}