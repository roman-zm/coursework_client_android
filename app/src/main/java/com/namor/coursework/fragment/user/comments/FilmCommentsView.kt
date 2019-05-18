package com.namor.coursework.fragment.user.comments

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface FilmCommentsView: MvpView {
    @StateStrategyType(SkipStrategy::class)
    fun finish()

}
