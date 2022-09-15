package com.iptvstreamer.weatherstation.ui.login

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.iptvstreamer.weatherstation.R
import com.iptvstreamer.weatherstation.databinding.FragmentLoginBinding
import com.iptvstreamer.weatherstation.library.controller.LoginController
import com.iptvstreamer.weatherstation.library.model.LoginResponse

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.email.editText?.doOnTextChanged { text, start, before, count ->
            binding.loginFailed.visibility = View.GONE
        }

        binding.password.editText?.doOnTextChanged { text, start, before, count ->
            binding.loginFailed.visibility = View.GONE
        }

        binding.progressBar.visibility = View.VISIBLE

        LoginController.refreshToken(getRefreshToken()) {
            handleLoginResponse(it, true)
        }

        binding.button.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            val email = binding.email.editText?.text?.toString() ?: ""
            val password = binding.password.editText?.text?.toString() ?: ""
            LoginController.login(email, password) {
                handleLoginResponse(it, false)
            }
        }
    }

    private fun handleLoginResponse(loginResponse: LoginResponse?, fromRefresh: Boolean) {
        binding.progressBar.visibility = View.GONE
        if (loginResponse != null) {
            saveRefreshToken(loginResponse.refresh_token)
            val action =
                LoginFragmentDirections.actionLoginFragmentToHomeFragment(loginResponse.access_token)
            findNavController().navigate(action)
        } else {
            if (!fromRefresh)
                binding.loginFailed.visibility = View.VISIBLE
        }
    }

    private fun saveRefreshToken(refreshToken: String) {
        val sharedPreferences = activity?.getSharedPreferences(
            context?.getString(R.string.login_sharedPreferences),
            MODE_PRIVATE
        )
        val editor = sharedPreferences?.edit()
        editor?.putString(
            context?.getString(R.string.login_sharedPreferences_refresh_token),
            refreshToken
        )
        editor?.apply()
    }

    private fun getRefreshToken(): String {
        val sharedPreferences = activity?.getSharedPreferences(
            context?.getString(R.string.login_sharedPreferences),
            MODE_PRIVATE
        )
        return sharedPreferences?.getString(
            context?.getString(R.string.login_sharedPreferences_refresh_token),
            ""
        ) ?: ""
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}