package com.namor.coursework

import android.app.Application
import com.namor.coursework.domain.Administrator
import retrofit2.Retrofit

class App: Application() {

    companion object {
        var currentAdmin: Administrator? = null
        val service = ServiceHolder()
    }
}