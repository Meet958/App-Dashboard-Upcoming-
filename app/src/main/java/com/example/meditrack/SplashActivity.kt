package com.example.meditrack

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.dashboard.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // This shows your splash layout
        setContentView(R.layout.activity_splash)

        // Wait for 2 seconds, then open LoginActivity
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // ✅ So you can't go back to splash
        }, 2000) // 2000 milliseconds = 2 seconds
    }
}
