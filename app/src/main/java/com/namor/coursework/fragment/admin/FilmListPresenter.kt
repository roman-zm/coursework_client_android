package com.namor.coursework.fragment.admin

import com.arellomobile.mvp.InjectViewState
import com.namor.coursework.App
import com.namor.coursework.domain.Film
import com.namor.coursework.fragment.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@InjectViewState
class FilmListPresenter: BasePresenter<FilmListView>() {

    fun loadFilmList() {
        compositeDisposable += App.service.adminService?.getFilmList()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe( ::setFilmList, ::onError)
    }

    private fun setFilmList(films: List<Film>) {
        viewState.setFilms(films)
    }

    private fun onError(throwable: Throwable) {
        throwable.printStackTrace()
    }

}


