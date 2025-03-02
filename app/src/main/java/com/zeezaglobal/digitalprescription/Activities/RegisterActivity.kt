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

class RegisterActivity : AppCompatActivity() {
    private val authViewModel: UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
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

        // Handle the Register Button click
        registerButton.setOnClickListener {
            val email = emailEditText.text.toString()

            val password = passwordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                // Call the ViewModel's register function (with correct order of arguments)
                progressBar.visibility = ProgressBar.VISIBLE
                authViewModel.register(email, password)
                    .observe(this, Observer { responseMessage ->
                        progressBar.visibility = ProgressBar.GONE
                        if (responseMessage != null) {
                            if (responseMessage.message != "") {
                                Toast.makeText(
                                    this,
                                    "" + responseMessage.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                                val intent = Intent(this, LoginActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(
                                    this,
                                    "Register Failed" + responseMessage.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            progressBar.visibility = ProgressBar.GONE
                            Toast.makeText(this, "User Already Exists", Toast.LENGTH_SHORT).show()
                        }
                    })
            } else {
                progressBar.visibility = ProgressBar.GONE
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}