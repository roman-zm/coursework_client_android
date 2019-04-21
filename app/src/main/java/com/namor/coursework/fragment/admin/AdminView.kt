package com.namor.coursework.fragment.admin

import com.arellomobile.mvp.MvpView
import com.namor.coursework.domain.Film

interface AdminView: MvpView {
    fun setFilms(films: List<Film>)
}
