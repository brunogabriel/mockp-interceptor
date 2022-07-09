package io.github.brunogabriel.sample

import android.app.Application

class CustomApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        RetrofitProvider.setup(this)
    }
}