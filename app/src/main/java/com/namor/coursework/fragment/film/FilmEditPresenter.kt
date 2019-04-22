package com.namor.coursework.fragment.film

import com.arellomobile.mvp.InjectViewState
import com.namor.coursework.App
import com.namor.coursework.domain.Film
import com.namor.coursework.fragment.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@InjectViewState
class FilmEditPresenter: BasePresenter<FilmEditView>() {
    private var film: Film? = null

    fun loadFilm(film: Film?) {
        this.film = film
    }

    fun getInfo() {
        val f = film
        if (f != null) {
            viewState.loadFilm(f)
        } else {
            viewState.initForNewFilm()
        }
    }

    private fun updateFilm(id: Int, film: Film) {
        val admin = App.currentAdmin?.login ?: ""
        val f = film.copy(loginAdmin = admin, id = id)
        App.service.adminService?.updateFilm(id, f)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ complete() }, ::onServiceError)
                ?.let { compositeDisposable += it }
    }

    fun deleteFilm() {
        film?.id?.let { id ->
            App.service.adminService?.deleteFilm(id)
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe(::complete, ::onServiceError)
                    ?.let { compositeDisposable += it }
        }
    }

    private fun onServiceError(throwable: Throwable) {
        throwable.printStackTrace()
        viewState.showError(throwable.localizedMessage)
    }

    private fun complete() {
        viewState.close()
    }

    private fun complete(film: Film) {
        viewState.close()
    }

    fun uploadFilm(filmModel: Film?) {
        filmModel?.let {
            val f = film
            if (f != null) {
                updateFilm(f.id, it)
            } else {
                addFilm(it)
            }
        }
    }

    private fun addFilm(film: Film) {
        val admin = App.currentAdmin?.login ?: ""
        val f = film.copy(loginAdmin = admin)
        App.service.adminService?.addFilm(f)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ complete() }, ::onServiceError)
                ?.let { compositeDisposable += it }
    }

}
