package com.eduardossampaio.toprepos.views.acitivties

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.eduardossampaio.toprepos.R
import com.eduardossampaio.toprepos.appModule
import com.eduardossampaio.toprepos.impls.flows.github.GitRepositoriesFlow
import com.eduardossampaio.toprepos.views.acitivties.list_repositories.ListRepositoriesActivity
import com.eduardossampaio.toprepos.views.interactors.SplashInteractor
import com.eduardossampaio.toprepos.views.presenters.SplashPresenter
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import java.util.Timer
import kotlin.concurrent.timerTask


class SplashActivity : AppCompatActivity(), SplashPresenter {
    private val onSplashPresenterFinishedPublisher = PublishSubject.create<Any>()
    override val onSplashPresenterFinished: Observable<Any>  = onSplashPresenterFinishedPublisher
    private val interactor:SplashInteractor by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        startKoin{
            androidLogger()
            androidContext(this@SplashActivity)
            modules(appModule)
        }

        interactor.bind(this)
        interactor.start()

        Timer().schedule(timerTask {startFlow()}, 3000)
    }

    private fun startFlow(){
        onSplashPresenterFinishedPublisher.onNext(this)
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showError(error: Throwable) {

    }
}