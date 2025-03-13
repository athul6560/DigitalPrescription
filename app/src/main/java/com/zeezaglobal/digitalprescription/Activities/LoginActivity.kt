package com.zeezaglobal.digitalprescription.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.stripe.android.PaymentConfiguration
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
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val PayBtn = findViewById<Button>(R.id.button5)
        PaymentConfiguration.init(
            applicationContext,
            "pk_test_51QB0leCsOvBUMpCYLGzCsPDnPdyRI7XwsJ2fLuEBDRAQQl7LqvK3kTCT0AJwP40dKPK28Ghs7HkLtfEhBwiiNpAx00ElUqv6HL"
        )
        PayBtn.setOnClickListener {
            val intent = Intent(this, SubscriptionActivity::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                progressBar.visibility = ProgressBar.VISIBLE
                authViewModel.login(email, password).observe(this, Observer { token ->
                    progressBar.visibility = ProgressBar.GONE
                    if (token != null) {
                        if (!token.token.isNullOrEmpty()) {
                            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()

                            // Save token in SharedPreferences
                            getSharedPreferences("APP_PREFS", MODE_PRIVATE)
                                .edit()
                                .putString("jwt_token", token.token) // `token` is already a String
                                .apply()
                            getSharedPreferences("APP_PREFS", MODE_PRIVATE)
                                .edit()
                                .putInt("user_id", token.user.id) // `token` is already a String
                                .apply()
                            val intent = Intent(this, DoctorDeatilsActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            } else {
                progressBar.visibility = ProgressBar.GONE
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}