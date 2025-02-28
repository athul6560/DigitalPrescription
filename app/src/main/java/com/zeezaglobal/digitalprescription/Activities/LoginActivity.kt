package com.zeezaglobal.digitalprescription.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.zeezaglobal.digitalprescription.R
import com.zeezaglobal.digitalprescription.ViewModel.UserViewModel

class LoginActivity : AppCompatActivity() {
    private val authViewModel: UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_intro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val emailEditText = findViewById<EditText>(R.id.email_edittext)
        val passwordEditText = findViewById<EditText>(R.id.password_edittext)
        val loginButton = findViewById<Button>(R.id.login_btn)
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                authViewModel.login(email, password).observe(this, Observer { token ->
                    if (!token.isNullOrEmpty()) {
                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()

                        // Save token in SharedPreferences
                        getSharedPreferences("APP_PREFS", MODE_PRIVATE)
                            .edit()
                            .putString("jwt_token", token) // `token` is already a String
                            .apply()
                        val intent = Intent(this, DashboardActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}