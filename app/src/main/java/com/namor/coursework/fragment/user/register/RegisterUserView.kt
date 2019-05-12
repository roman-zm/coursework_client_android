package com.namor.coursework.fragment.user.register

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface RegisterUserView: MvpView {
    fun back()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setFioError(fioValid: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setEmailError(emailValid: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setLoginError(loginValid: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setIsValid(inputValid: Boolean)

}
