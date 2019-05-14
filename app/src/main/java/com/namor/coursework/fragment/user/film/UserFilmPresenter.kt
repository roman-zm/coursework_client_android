package com.namor.coursework.fragment.user.film

import com.arellomobile.mvp.InjectViewState
import com.namor.coursework.App
import com.namor.coursework.domain.Film
import com.namor.coursework.domain.Purchase
import com.namor.coursework.fragment.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

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

}
