package com.esampaio.core.usecases.repositories

import android.util.Log
import com.esampaio.core.presenters.repositories.ShowRepositoriesPresenter
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
    lateinit var showRepositoriesPresenter: ShowRepositoriesPresenter

    private var onRepoSelectedSubscriber: Disposable? = null
    private var onPageChancedSubscriber: Disposable? = null

    private val searchQuery = SearchQuery(Languages.Java,SortType.stars);
    override fun start() {

        setupObservers()

        showRepositoriesPresenter.showLoading();

        fetchRepositories(0)

    }

    override fun finish() {
        onRepoSelectedSubscriber?.dispose()
        onPageChancedSubscriber?.dispose()
    }

    private fun setupObservers(){
        onRepoSelectedSubscriber =
            showRepositoriesPresenter.onRepositoryClicked.observeOn(AndroidSchedulers.mainThread())
                .subscribe {repo ->

                }
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
                /* onNext = */ {
                    Log.d("SplashActivity", "list all repos")
                    showRepositoriesPresenter.showRepositories(it)
                },
                /* onError = */ {
                    it.printStackTrace()
                    it.message?.let { it1 -> Log.e("SplashActivity", it1) }
                    showRepositoriesPresenter.showError(it)
                }
            )
        subscribe.dispose()
    }
}