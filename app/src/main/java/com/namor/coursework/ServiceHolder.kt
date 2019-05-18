package com.namor.coursework

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.namor.coursework.domain.Film
import com.namor.coursework.domain.Page
import com.namor.coursework.service.AdminService
import com.namor.coursework.service.PageDeserializer
import com.namor.coursework.service.UserService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class ServiceHolder {
//    private val filmListType = object : TypeToken<List<Film>>() {}.type

    private val gson = GsonBuilder().apply {
        registerTypeAdapter(
                Page::class.java, PageDeserializer()
        )
        registerTypeAdapter(
                Calendar::class.java, CalendarDeserializer()
        )

    }.create()

    private var retrofit: Retrofit? = null

    var adminService: AdminService? = null
        private set

    var userService: UserService? = null
        private set

    fun setUrlAndInit(url: String): Boolean {
        if (adminService != null) return true

        retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        adminService = retrofit?.create(AdminService::class.java)
        userService = retrofit?.create(UserService::class.java)

        return adminService != null && userService != null
    }
}
