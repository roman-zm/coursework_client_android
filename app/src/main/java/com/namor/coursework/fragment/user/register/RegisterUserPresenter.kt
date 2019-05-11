package com.namor.coursework.fragment.user.register

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.namor.coursework.App
import com.namor.coursework.domain.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

@InjectViewState
class RegisterUserPresenter: MvpPresenter<RegisterUserView>() {

    private val disposable = CompositeDisposable()

    fun register(login: String, fio: String, email: String, sex: Char) {
        disposable += App.service.userService?.addUser(
                User(login, fio, sex, email)
        )?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(::success, ::error)
    }

    private fun success() {}

    private fun error(throwable: Throwable) {
        throwable.printStackTrace()
        viewState.back()
    }

}

private operator fun CompositeDisposable.plusAssign(disposable: Disposable?) {
    disposable?.let { this.add(it) }
}
