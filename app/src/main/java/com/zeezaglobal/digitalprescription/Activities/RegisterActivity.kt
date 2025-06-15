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
import com.zeezaglobal.digitalprescription.R
import com.zeezaglobal.digitalprescription.ViewModel.UserViewModel
import android.util.Patterns

class RegisterActivity : AppCompatActivity() {
    private val authViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Get references to the UI elements
        val emailEditText = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val passwordEditText = findViewById<EditText>(R.id.editTextTextPassword)
        val registerButton = findViewById<Button>(R.id.button)
        val confirmPasswordEditText = findViewById<EditText>(R.id.editTextConfirmPassword)
        // Handle the Register Button click

        authViewModel.buttonStatus.observe(this) { status ->
            if (status != null) {
                registerButton.isEnabled = status
            }
        }


        registerButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString().trim()
            authViewModel.register(email, password, confirmPassword).observe(this) { response ->
                if (response != null) {
                    registerButton.isEnabled = response.status
                    if (response.status) {

                        Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                        finish()

                    } else {
                        Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }
}



