package com.namor.coursework.fragment.admin

import com.arellomobile.mvp.InjectViewState
import com.namor.coursework.App
import com.namor.coursework.domain.Film
import com.namor.coursework.fragment.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@InjectViewState
class FilmListPresenter: BasePresenter<FilmListView>() {

    private var filmList: List<Film> = listOf()
    private var filtered: List<Film> = listOf()

    fun loadFilmList() {
        compositeDisposable += App.service.adminService?.getFilmList()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doAfterSuccess(::saveFilmList)
                ?.subscribe( ::setFilmList, ::onError)
    }

    fun onFilter(filter: String) {
        if (filter.isBlank()) {
            setFilmList(filmList)
        } else {
            filtered = filmList.filter {
                matchFilter(it, filter.toLowerCase())
            }
            setFilmList(filtered)
        }
    }

    private fun matchFilter(film: Film, filter: String): Boolean {
        return when {
            film.name.containsIgnoreCase(filter) -> true
            film.director.containsIgnoreCase(filter) -> true
            film.actors.containsIgnoreCase(filter) -> true
            film.year.toString().containsIgnoreCase(filter) -> true
            film.genres.any { it.name.containsIgnoreCase(filter) } -> true
            else -> false
        }
    }

    private fun saveFilmList(films: List<Film>) {
        this.filmList = films
    }

    private fun setFilmList(films: List<Film>) {
        viewState.setFilms(films)
    }

    private fun onError(throwable: Throwable) {
        throwable.printStackTrace()
    }

}

private fun String.containsIgnoreCase(contains: String): Boolean {
    return this.toLowerCase().contains(contains.toLowerCase())
}


