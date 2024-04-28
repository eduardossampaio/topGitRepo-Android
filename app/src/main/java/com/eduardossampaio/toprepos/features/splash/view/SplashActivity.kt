package com.eduardossampaio.toprepos.features.splash.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eduardossampaio.toprepos.R
import com.eduardossampaio.toprepos.appModule
import com.eduardossampaio.toprepos.features.splash.presenter.SplashPresenter
import com.eduardossampaio.toprepos.features.splash.interactor.SplashInteractor
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
    private val interactor: SplashInteractor by inject()

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