package com.example.meditrack

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.api.LoginResponse
import com.example.api.RetrofitClient
import com.example.api.SignupRequest
import com.example.dashboard.MainActivity
import com.example.dashboard.R
import com.example.utils.TokenManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val nameEditText = findViewById<EditText>(R.id.fullNameEditText)
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val signupButton = findViewById<Button>(R.id.signupButton)
        val loginText = findViewById<TextView>(R.id.loginRedirectText)

        signupButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString()


            // Basic validation
            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }



            // Create request matching backend expected fields: { name, email, password }
            val request = SignupRequest(name, email, password)

            // Call register API
            RetrofitClient.getInstance(this).register(request)
                .enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        if (response.isSuccessful) {
                            val token = response.body()?.token
                            if (!token.isNullOrEmpty()) {
                                TokenManager.saveToken(this@SignUpActivity, token)
                                Toast.makeText(this@SignUpActivity, "Signup Success", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this@SignUpActivity, MainActivity::class.java))
                                finish()
                            } else {
                                Toast.makeText(this@SignUpActivity, "Signup succeeded but no token returned", Toast.LENGTH_LONG).show()
                            }
                        } else {
                            // Read error body to show real reason
                            val err = try {
                                response.errorBody()?.string()
                            } catch (e: Exception) {
                                null
                            }
                            Toast.makeText(this@SignUpActivity, "Signup Failed: ${err ?: response.code()}", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(this@SignUpActivity, "Network error: ${t.message}", Toast.LENGTH_LONG).show()
                    }
                })
        }

        loginText.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
