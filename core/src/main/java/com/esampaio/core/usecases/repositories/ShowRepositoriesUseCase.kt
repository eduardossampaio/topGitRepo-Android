package com.esampaio.core.usecases.repositories

import android.util.Log
import com.esampaio.core.services.gitService.GitApiService
import com.esampaio.core.services.gitService.models.Languages
import com.esampaio.core.services.gitService.models.SearchQuery
import com.esampaio.core.services.gitService.models.SortType
import com.esampaio.core.usecases.UseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class ShowRepositoriesUseCase : UseCase{

    lateinit var gitApiService: GitApiService
    lateinit var showRepositoriesPresenter: ShowRepositoriesInteractor

    private var onPageChancedSubscriber: Disposable? = null

    private val searchQuery = SearchQuery(Languages.Java,SortType.stars);
    override fun start() {
        setupObservers()
        fetchRepositories(0)
    }

    override fun finish() {
        onPageChancedSubscriber?.dispose()
    }

    private fun setupObservers(){

        onPageChancedSubscriber =
            showRepositoriesPresenter.onPageChanged.observeOn(AndroidSchedulers.mainThread())
                .subscribe {page ->
                    fetchRepositories(page)
                }
    }

    private fun fetchRepositories(page:Int){
        val subscribe = gitApiService.listAllRepositories(page,searchQuery)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                /* onNext = */ {repos ->
                    Log.d("SplashActivity", "list all repos")
                    showRepositoriesPresenter.showRepositories(repos)
                },
                /* onError = */ {
                    it.printStackTrace()
                    it.message?.let { it1 -> Log.e("SplashActivity", it1) }
                    showRepositoriesPresenter.notifyError(it)
                }
            )
        //subscribe.dispose()
    }
}