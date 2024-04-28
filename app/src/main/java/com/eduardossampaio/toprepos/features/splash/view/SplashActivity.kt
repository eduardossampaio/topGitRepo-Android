package com.eduardossampaio.toprepos.features.splash.view


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

    lateinit var animationView:LottieAnimationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        animationView = findViewById<LottieAnimationView>(R.id.loading)
//        stopKoin()
//        startKoin{
//            androidLogger()
//            androidContext(this@SplashActivity)
//            modules(appModule)
//        }


        interactor.bind(this)
        interactor.start()
//        setupLottieAnimation()

        Timer().schedule(timerTask {startFlow()}, 5000)
    }

    private fun setupLottieAnimation(){
        val animationView = findViewById<LottieAnimationView>(R.id.loading)

        val drawable = LottieDrawable()

        LottieComposition.Factory.fromAssetFileName(
            this, "github_splash.json"
        ) { composition: LottieComposition? ->
            drawable.setComposition(composition)
            drawable.playAnimation()
            drawable.setScale(3f)
            animationView.setImageDrawable(drawable)
        }
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
}