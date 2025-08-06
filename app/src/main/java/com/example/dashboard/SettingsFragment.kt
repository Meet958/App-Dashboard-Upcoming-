package com.example.dashboard.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.meditrack.LoginActivity
import com.example.dashboard.R
import android.widget.Toast

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        view.findViewById<Button>(R.id.btnEditProfile).setOnClickListener {
            Toast.makeText(requireContext(), "Edit Profile clicked", Toast.LENGTH_SHORT).show()
            // Navigate to EditProfileActivity if you have it
        }

        view.findViewById<Button>(R.id.btnChangePassword).setOnClickListener {
            Toast.makeText(requireContext(), "Change Password clicked", Toast.LENGTH_SHORT).show()
        }

        view.findViewById<Button>(R.id.btnNotifications).setOnClickListener {
            Toast.makeText(requireContext(), "Notification Settings clicked", Toast.LENGTH_SHORT).show()
        }

        view.findViewById<Button>(R.id.btnPrivacyPolicy).setOnClickListener {
            Toast.makeText(requireContext(), "Privacy Policy clicked", Toast.LENGTH_SHORT).show()
        }

        view.findViewById<Button>(R.id.btnLogout).setOnClickListener {
            // Clear login session if needed
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }

        return view
    }
}
