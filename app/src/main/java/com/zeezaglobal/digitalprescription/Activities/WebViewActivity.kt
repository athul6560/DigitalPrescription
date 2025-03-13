package com.zeezaglobal.digitalprescription.Activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.zeezaglobal.digitalprescription.R

class WebViewActivity : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val webView = WebView(this)
        setContentView(webView)

        // Enable JavaScript
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true

        // Get the session URL from intent
        val sessionUrl = intent.getStringExtra("sessionUrl")

        // Load Stripe checkout session
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                url?.let { view?.loadUrl(it) }
                return true
            }
        }
        webView.webChromeClient = WebChromeClient()

        if (sessionUrl != null) {
            webView.loadUrl(sessionUrl)
        } else {
            finish() // Close activity if URL is missing
        }
    }
}