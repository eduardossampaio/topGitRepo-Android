package com.esampaio.core.usecases.repositories.list

import android.util.Log
import com.esampaio.core.models.Repo
import com.esampaio.core.services.gitService.GitApiService
import com.esampaio.core.services.gitService.models.Languages
import com.esampaio.core.services.gitService.models.SearchQuery
import com.esampaio.core.services.gitService.models.SortType
import com.esampaio.core.usecases.UseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject

open class ShowRepositoriesUseCase(private var gitApiService: GitApiService) : UseCase<Unit?, List<Repo>>{

    private val searchQuery = SearchQuery(Languages.Java,SortType.stars);
    var resultSubject = PublishSubject.create<List<Repo>>()
    private var githubDiposable: Disposable? = null
    private var page = 0;
    override fun start(params: Unit?):Observable<List<Repo>> {
        fetchRepositories(0)
        return resultSubject;

    }

    private fun fetchRepositories(page:Int){
        githubDiposable = gitApiService.listAllRepositories(page,searchQuery)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                /* onNext = */ {repos ->
                    resultSubject.onNext(repos)
                },
                /* onError = */ {
                    it.printStackTrace()
                    resultSubject.onError(it)
                }
            )
    }

    fun loadMore(){
        page++
        fetchRepositories(page);
    }
    override fun finish() {
        githubDiposable?.dispose()
    }
}