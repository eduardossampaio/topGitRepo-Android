package com.esampaio.core.usecases.repositories.pr

import com.esampaio.core.models.PullRequest
import com.esampaio.core.models.Repo
import com.esampaio.core.services.gitService.GitApiService
import com.esampaio.core.usecases.UseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

interface ListPRUseCase : UseCase<Repo, List<PullRequest>>{

}
class ListPRUseCaseImpl(private var gitApiService: GitApiService) : ListPRUseCase{


    override fun start(params: Repo):Observable<List<PullRequest>> {
        return gitApiService.listPullRequests(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun finish() {

    }

}
