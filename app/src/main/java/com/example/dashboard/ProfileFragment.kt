package com.example.dashboard.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.meditrack.LoginActivity
import com.example.dashboard.R


class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Find and set up buttons
        val btnEditProfile = view.findViewById<Button>(R.id.btnEditProfile)
        val btnChangeProfile = view.findViewById<Button>(R.id.btnChangeProfile)
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)

        // Handle edit profile button
        btnEditProfile.setOnClickListener {
            Toast.makeText(requireContext(), "Edit Profile clicked", Toast.LENGTH_SHORT).show()
            // You can launch an EditProfileActivity here
        }

        // Handle change profile photo button
        btnChangeProfile.setOnClickListener {
            Toast.makeText(requireContext(), "Change Profile Photo clicked", Toast.LENGTH_SHORT).show()
            // Launch image picker or camera
        }

        // Handle logout
        btnLogout.setOnClickListener {
            Toast.makeText(requireContext(), "Logging out...", Toast.LENGTH_SHORT).show()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        return view
    }
}
