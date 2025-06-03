package com.zeezaglobal.digitalprescription.Application

import android.app.Application
import com.zeezaglobal.digitalprescription.RestApi.TokenManager

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        TokenManager.init(this)
    }
}