package com.namor.coursework.fragment

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenter<T: MvpView>: MvpPresenter<T>() {
    val compositeDisposable = CompositeDisposable()


    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }


    operator fun CompositeDisposable.plusAssign(disposable: Disposable?) {
        disposable?.let { add(it) }
    }
}


