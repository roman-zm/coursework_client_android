package com.namor.coursework

import android.app.Application
import com.namor.coursework.domain.Administrator
import com.namor.coursework.domain.User
import retrofit2.Retrofit

class App: Application() {

    companion object {
        var currentUser: User? = null
        var currentAdmin: Administrator? = null
        val service = ServiceHolder()
    }
}