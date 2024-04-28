package com.eduardossampaio.toprepos.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eduardossampaio.toprepos.R
import com.eduardossampaio.toprepos.impls.services.github.GithubApiService
import com.eduardossampaio.toprepos.views.list_repositories.ListRepositoriesActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

//        val githuService = GithubApiService()
//
//        githuService.teste();

        val intent = Intent(this, ListRepositoriesActivity::class.java)
        startActivity(intent);

//        val api: GitApiService = GithubApiService()
//
//        val subscribe = api.listAllRepositories(0,null)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                /* onNext = */ {
//                    Log.d("SplashActivity", "list all repos")
//                },
//                /* onError = */ {
//                    it.printStackTrace()
//                    it.message?.let { it1 -> Log.e("SplashActivity", it1) }
//                }
//            )

//        subscribe.dispose()
//            .subscribe {
//                Log.d("SplashActivity","list all repos")
//
//            }
    }
}