package com.namor.coursework.fragment.film

import com.arellomobile.mvp.InjectViewState
import com.namor.coursework.App
import com.namor.coursework.domain.Film
import com.namor.coursework.domain.Genre
import com.namor.coursework.fragment.BasePresenter
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@InjectViewState
class FilmEditPresenter: BasePresenter<FilmEditView>() {
    private var genres: List<Genre> = listOf()
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
        initPredictions()
    }

    private fun initPredictions() {
        App.service.adminService?.getGenres()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(::setPredictions, ::onServiceError)
                ?.let { compositeDisposable += it }
    }

    private fun setPredictions(genres: List<Genre>) {
        this.genres = genres
        val predictions = createPredictionsArray(genres)
        viewState.setPredictions(predictions)
    }

    private fun createPredictionsArray(genres: List<Genre>) = genres.map { it.name }.toTypedArray()

    private fun updateFilm(id: Int, film: Film) {
        val admin = App.currentAdmin?.login ?: ""
        val f = film.copy(loginAdmin = admin, id = id)

        val delete = App.service.adminService?.deleteFilmGenres(id)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())

        val update = App.service.adminService?.updateFilm(id, f)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())

        if (this.film?.genres?.isNotEmpty() == true) {
            delete?.andThen(update)
                    ?.onErrorResumeNext { update }
        } else {
            update
        }?.subscribe({ complete() }, ::onServiceError)?.let { compositeDisposable += it }

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

    fun getGenresSet(chipValues: List<String>) = genres.filter { it.name in chipValues }.toSet()

}
