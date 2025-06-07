package com.zeezaglobal.digitalprescription.Application

import android.app.Application
import com.zeezaglobal.digitalprescription.SharedPreference.TokenManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        TokenManager.init(this)
    }
}