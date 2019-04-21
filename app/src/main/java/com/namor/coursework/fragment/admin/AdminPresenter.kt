package com.namor.coursework.fragment.admin

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.namor.coursework.App
import com.namor.coursework.domain.Film
import com.namor.coursework.fragment.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

@InjectViewState
class AdminPresenter: BasePresenter<AdminView>() {

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


