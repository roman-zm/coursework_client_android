package com.namor.coursework.fragment.login

import com.arellomobile.mvp.MvpView
import com.namor.coursework.domain.Administrator

interface LoginView: MvpView {
    fun setError(s: String)
    fun openAdmin(administrator: Administrator)
}
