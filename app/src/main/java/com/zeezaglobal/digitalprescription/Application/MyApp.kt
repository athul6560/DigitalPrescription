package com.zeezaglobal.digitalprescription.Application

import android.app.Application
import com.zeezaglobal.digitalprescription.SharedPreference.TokenManager
import com.zeezaglobal.digitalprescription.SharedPreference.UserId
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        TokenManager.init(this)
        UserId.init(this)
    }
}