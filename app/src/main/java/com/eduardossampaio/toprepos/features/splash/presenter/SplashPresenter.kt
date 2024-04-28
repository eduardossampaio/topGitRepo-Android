package com.eduardossampaio.toprepos.features.splash.presenter

import com.eduardossampaio.toprepos.views.presenters.BasePresenter
import io.reactivex.rxjava3.core.Observable

interface SplashPresenter : BasePresenter {
    val onSplashPresenterFinished: Observable<Any>
}