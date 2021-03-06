package com.namor.coursework.fragment.login

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.google.android.material.circularreveal.CircularRevealHelper
import com.namor.coursework.domain.Administrator
import com.namor.coursework.domain.User

interface LoginView: MvpView {
    fun showMessage(s: String)
    fun openAdminScreen(administrator: Administrator)
    fun openUserScreen(user: User)

    @StateStrategyType(SkipStrategy::class)
    fun registerUser(login: String)
}
