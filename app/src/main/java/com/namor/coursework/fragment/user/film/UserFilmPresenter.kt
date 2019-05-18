package com.namor.coursework.fragment.user.film

import com.arellomobile.mvp.InjectViewState
import com.namor.coursework.App
import com.namor.coursework.domain.Film
import com.namor.coursework.domain.Purchase
import com.namor.coursework.domain.Rating
import com.namor.coursework.fragment.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlin.math.roundToInt

@InjectViewState
class UserFilmPresenter: BasePresenter<UserFilmView>() {
    private lateinit var film: Film

    fun setFilm(film: Film?) {
        if (film != null) {
            this.film = film

            with (viewState) {
                setTitle(film.name)
                setInfo(film.director, film.year, film.duration)
                setGenres(film.genres)
                setPrice(film.price)
                setActors(film.actors)
            }
            
            loadPurchase()
            loadRating()
        }
    }

    fun purchase() {
        val user = App.currentUser
        if (user != null) {
            compositeDisposable += App.service.userService?.addPurchase(
                    Purchase(film = film.id, user = user.login)
            )?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe({ setPurchased(true) }, { setPurchased(false) })
        }
    }

    private fun loadRating() {
        loadAverage()
        loadCount()
    }

    private fun loadCount() {
        val user = App.currentUser
        if (user != null) {
            compositeDisposable += App.service.userService
                    ?.getRatingCount(film.id)
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe(::setMarkCount) { setMarkCount(0) }
        }
    }

    private fun setMarkCount(count: Int) {
        viewState.setCount(count)
    }

    private fun loadAverage() {
        val user = App.currentUser
        if (user != null) {
            compositeDisposable += App.service.userService
                    ?.getAverageRating(film.id)
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe(::setMark) { setMark(0f) }
        }
    }

    private fun setMark(mark: Float) {
        viewState.setMark(mark)
    }

    private fun loadPurchase() {
        val user = App.currentUser
        if (user != null) {
            compositeDisposable += App.service.userService?.getPurchase(
                    film = film.id, user = user.login
            )?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe({ setPurchased(true) }, { setPurchased(false) })
        }
    }

    private fun setPurchased(isPurchased: Boolean) {
        if (isPurchased) viewState.setSeeButton()
        else viewState.setPrice(film.price)
    }

    fun onRatingChanged(rating: Float) {
        val user = App.currentUser
        if (user != null) {
            compositeDisposable += App.service.userService
                    ?.changeRating(Rating(user.login, film.id, rating.roundToInt()))
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe()
        }
    }

}
