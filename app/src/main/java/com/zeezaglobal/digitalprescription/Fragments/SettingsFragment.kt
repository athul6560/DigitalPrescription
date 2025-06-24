package com.zeezaglobal.digitalprescription.Fragments

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.zeezaglobal.digitalprescription.Activities.LoginActivity
import com.zeezaglobal.digitalprescription.R
import com.zeezaglobal.digitalprescription.SharedPreference.TokenManager

class SettingsFragment : Fragment() {



    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        val logoutBtn = view.findViewById<Button>(R.id.logout_btn)

        logoutBtn.setOnClickListener {
            // Clear JWT token from SharedPreferences
           TokenManager.clearToken()

            // Start LoginActivity and clear back stack
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        return view
    }
}