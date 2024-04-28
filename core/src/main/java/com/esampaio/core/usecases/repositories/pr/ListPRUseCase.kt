package com.esampaio.core.usecases.repositories.pr

import com.esampaio.core.models.Repo
import com.esampaio.core.services.gitService.GitApiService
import com.esampaio.core.usecases.UseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

interface ListPRUseCase : UseCase<Repo>{
    var interactor: ListPRUseCaseInteractor
}
class ListPRUseCaseImpl(private var gitApiService: GitApiService) : ListPRUseCase{
    override lateinit var interactor: ListPRUseCaseInteractor

    override fun start(params: Repo) {
        this.fetchPullRequest(params)
    }

    override fun finish() {

    }

    private fun fetchPullRequest(repo: Repo){
        val disposable = gitApiService.listPullRequests(repo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                /* onNext = */ {prList ->
                    interactor.showPullRequestList(prList)
                },
                /* onError = */ {
                    it.printStackTrace()
                    interactor.notifyError(it)
                }
            )
    }

}
