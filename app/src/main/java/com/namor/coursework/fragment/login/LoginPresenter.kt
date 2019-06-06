package com.namor.coursework.fragment.login

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.namor.coursework.App
import com.namor.coursework.domain.Administrator
import com.namor.coursework.domain.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@InjectViewState
class LoginPresenter: MvpPresenter<LoginView>() {
    fun setHomeUrl(homeUrl: CharSequence?): Boolean {
        val success = App.service.setUrlAndInit(homeUrl.toString())
        if (!success) viewState.showMessage("invalid base url: $homeUrl")
        return success
    }

    fun onAdminLogin(login: CharSequence?) {
        login?.toString()?.let {
            App.service.adminService?.getAdmin(it)
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe(this::adminLogin, this::adminLoginError)
        }
    }

    fun onUserLogin(inLogin: CharSequence?) {
        inLogin?.toString()?.let { login ->
            App.service.userService?.getUser(login)
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe(this::userLogin) {
                        userLoginError(it, login)
                    }
        }
    }

    private fun adminLogin(administrator: Administrator) {
        viewState.openAdminScreen(administrator)
    }

    private fun userLogin(user: User) {
        viewState.showMessage(user.toString())
        viewState.openUserScreen(user)
    }

    private fun userLoginError(throwable: Throwable, login: String) {
        throwable.printStackTrace()
        viewState.showMessage("Пользователь не найден")
        viewState.registerUser(login)
    }

    private fun adminLoginError(throwable: Throwable) {
        throwable.printStackTrace()
        viewState.showMessage(throwable.localizedMessage)
    }
}
