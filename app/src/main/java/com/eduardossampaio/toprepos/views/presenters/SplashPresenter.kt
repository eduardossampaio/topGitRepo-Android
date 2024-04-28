package com.eduardossampaio.toprepos.views.presenters

import com.esampaio.core.models.Repo
import io.reactivex.rxjava3.core.Observable

interface SplashPresenter : BasePresenter {
    val onSplashPresenterFinished: Observable<Any>
}