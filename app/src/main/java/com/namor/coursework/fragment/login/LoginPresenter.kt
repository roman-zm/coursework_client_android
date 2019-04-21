package com.namor.coursework.fragment.login

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.namor.coursework.App
import com.namor.coursework.domain.Administrator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@InjectViewState
class LoginPresenter: MvpPresenter<LoginView>() {
    fun setHomeUrl(homeUrl: CharSequence?): Boolean {
        val success = App.service.setUrlAndInit(homeUrl.toString())
        if (!success) viewState.setError("invalid base url: $homeUrl")
        return success
    }

    fun onAdminLogin(login: CharSequence?) {
        login?.toString()?.let {
            App.service.adminService?.getAdmin(it)
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe(
                            this::login, this::loginError
                    )
        }
    }

    private fun loginError(throwable: Throwable) {
        throwable.printStackTrace()
        viewState.setError(throwable.localizedMessage)
    }

    private fun login(administrator: Administrator) {
        viewState.setError(administrator.toString())
        viewState.openAdmin(administrator)
    }


}
