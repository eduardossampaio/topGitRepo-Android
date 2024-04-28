package com.eduardossampaio.toprepos.features.splash.interactor

import android.content.Context
import com.eduardossampaio.toprepos.features.splash.presenter.SplashPresenter
import com.eduardossampaio.toprepos.flow.Flow
import com.eduardossampaio.toprepos.flow.github.GitRepositoriesFlow
import com.eduardossampaio.toprepos.views.presenters.BasePresenter
import com.eduardossampaio.toprepos.views.interactors.BaseInteractor
import io.reactivex.rxjava3.disposables.Disposable

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface SplashInteractor : BaseInteractor, KoinComponent{
}

class SplashInteractorImpl(private val context: Context) : SplashInteractor {

    private var subscribe: Disposable? = null
    private lateinit var splashPresenter: SplashPresenter

    private val flow: GitRepositoriesFlow by inject()

    override fun bind(presenter: BasePresenter) {
       splashPresenter = presenter as SplashPresenter
    }

    override fun start() {
         subscribe = splashPresenter.onSplashPresenterFinished.subscribe {
            flow.start(context)
        }
    }

    override fun destroy() {

    }

}