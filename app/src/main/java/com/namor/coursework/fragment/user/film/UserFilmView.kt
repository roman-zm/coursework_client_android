package com.namor.coursework.fragment.user.film

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.namor.coursework.domain.Genre

interface UserFilmView: MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setTitle(name: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setInfo(director: String, year: Int, duration: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setGenres(genres: Set<Genre>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setPrice(price: Double)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setActors(actors: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setSeeButton()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setMark(mark: Float)

    @StateStrategyType(AddToEndSingleStrategy::class)
    abstract fun setCount(count: Int)
}
