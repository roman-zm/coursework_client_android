package com.namor.coursework.fragment.admin

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.namor.coursework.domain.Film

interface FilmListView: MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setFilms(films: List<Film>)
}
