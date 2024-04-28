package com.eduardossampaio.toprepos.features.splash.view


import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.LottieDrawable
import com.eduardossampaio.toprepos.R
import com.eduardossampaio.toprepos.appModule
import com.eduardossampaio.toprepos.features.splash.interactor.SplashInteractor
import com.eduardossampaio.toprepos.features.splash.presenter.SplashPresenter
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.parameter.parametersOf
import java.util.Timer
import kotlin.concurrent.timerTask


class SplashActivity : AppCompatActivity(), SplashPresenter {
    private val onSplashPresenterFinishedPublisher = PublishSubject.create<Any>()
    override val onSplashPresenterFinished: Observable<Any>  = onSplashPresenterFinishedPublisher
    private val interactor: SplashInteractor by inject {parametersOf(this)}

    private lateinit var animationView:LottieAnimationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        animationView = findViewById<LottieAnimationView>(R.id.loading)

        interactor.bind(this)
        interactor.start()

        Timer().schedule(timerTask {startFlow()}, 5000)
    }


    override fun onResume() {
        super.onResume()
        animationView.playAnimation()
    }
    private fun startFlow(){
        runOnUiThread {
            animationView.pauseAnimation()
        }
        onSplashPresenterFinishedPublisher.onNext(this)
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showError(error: Throwable) {

    }
    override fun getContext(): Context {
        return this;
    }
}