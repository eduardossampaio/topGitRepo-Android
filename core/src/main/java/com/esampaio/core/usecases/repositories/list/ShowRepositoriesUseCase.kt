package com.esampaio.core.usecases.repositories.list

import android.util.Log
import com.esampaio.core.services.gitService.GitApiService
import com.esampaio.core.services.gitService.models.Languages
import com.esampaio.core.services.gitService.models.SearchQuery
import com.esampaio.core.services.gitService.models.SortType
import com.esampaio.core.usecases.UseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

open class ShowRepositoriesUseCase(private var gitApiService: GitApiService) : UseCase<Unit?>{

    lateinit var showRepositoriesInteractor: ShowRepositoriesInteractor

    private var onPageChancedSubscriber: Disposable? = null

    private val searchQuery = SearchQuery(Languages.Java,SortType.stars);
    override fun start(params: Unit?) {
        setupObservers()
        fetchRepositories(0)
    }

    override fun finish() {
        onPageChancedSubscriber?.dispose()
    }

    private fun setupObservers(){

        onPageChancedSubscriber =
            showRepositoriesInteractor.onPageChanged.observeOn(AndroidSchedulers.mainThread())
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
                    showRepositoriesInteractor.showRepositories(repos)
                },
                /* onError = */ {
                    it.printStackTrace()
                    it.message?.let { it1 -> Log.e("SplashActivity", it1) }
                    showRepositoriesInteractor.notifyError(it)
                }
            )
        //subscribe.dispose()
    }
}