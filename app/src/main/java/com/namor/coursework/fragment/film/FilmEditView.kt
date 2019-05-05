package com.namor.coursework.fragment.film

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.namor.coursework.domain.Film

interface FilmEditView: MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun loadFilm(film: Film)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun initForNewFilm()

    fun showError(localizedMessage: String?)
    fun close()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setPredictions(predictions: Array<String>)
}
