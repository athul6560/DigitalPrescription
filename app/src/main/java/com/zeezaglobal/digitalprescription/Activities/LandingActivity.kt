package com.zeezaglobal.digitalprescription.Activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.zeezaglobal.digitalprescription.R

class LandingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val loginBtn: AppCompatButton = findViewById(R.id.login_btn)
        val registerBtn: AppCompatButton = findViewById(R.id.register_btn)

        // Set an onClickListener on the "Next" button
        loginBtn.setOnClickListener {
            // Create an intent to navigate to the new activity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        registerBtn.setOnClickListener {
            // Create an intent to navigate to the new activity
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}