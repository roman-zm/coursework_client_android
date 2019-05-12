package com.namor.coursework.fragment.user.register

import android.util.Patterns
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

    private var lastLogin: String = ""
    private val disposable = CompositeDisposable()

    private var isEmailValid = false
    private var isFioValid = false
    private var isLoginValid = false

    private val userService = App.service.userService

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

    fun checkLogin(login: String) {
        if (login.isNotBlank() && login != "null" && login != lastLogin) {
            isLoginValid = false
            disposable += userService?.getUser(login)
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe(::userExists) { userNotExists(login, it) }
        } else {
            checkInput()
        }
        lastLogin = login
    }

    fun checkFio(fio: String) {
        isFioValid = fio.isNotBlank() && fio != "null"
        viewState.setFioError(isFioValid)
        checkInput()
    }

    fun checkEmail(email: String) {
        isEmailValid = email.isNotBlank()
                && email != "null"
                && Patterns.EMAIL_ADDRESS.matcher(email).matches()

        viewState.setEmailError(isEmailValid)
        checkInput()
    }

    private fun userNotExists(login: String, throwable: Throwable) {
        isLoginValid = true
        viewState.setLoginError(true)
        checkInput()
    }

    private fun userExists(user: User) {
        isLoginValid = false
        viewState.setLoginError(false)
        checkInput()
    }

    private fun checkInput() {
        viewState.setIsValid(isEmailValid && isFioValid && isLoginValid)
    }

}

private operator fun CompositeDisposable.plusAssign(disposable: Disposable?) {
    disposable?.let { this.add(it) }
}
