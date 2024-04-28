package com.eduardossampaio.toprepos.impls.services.github


import android.util.Log
import com.esampaio.core.models.PullRequest
import com.esampaio.core.models.Repo
import com.esampaio.core.services.gitService.GitApiService
import com.esampaio.core.services.gitService.models.SearchQuery
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.toObservable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
//import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.Date


class GithubApiService : GitApiService {
    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(JacksonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    override fun listAllRepositories(page: Int, searchQuery: SearchQuery?): Observable<List<Repo>> {
        val service =  this.retrofit.create(GithubApiServiceRetrofit::class.java)

        val observable = service.listRepos("language:Java",searchQuery?.sortBy.toString(),page);
        return observable.map { it.items!!.map {
                item -> Repo(
            item.id!!.toLong(), item.name!!, item.description!!, item.owner!!.login!!,item.owner!!.avatarUrl!!,
            item.stargazersCount!!.toInt(),item.forksCount!!.toLong(), emptyList())

          }}.toObservable()
    }
    override fun listPullRequests(repo: Repo): Observable<List<PullRequest>> {
        val service =  this.retrofit.create(GithubApiServiceRetrofit::class.java)

        val observable = service.listPullRequests(repo.authorName,repo.name);
        return observable.map { it.map {
                item -> PullRequest(
                    item.id!!,
                    item.repoItem!!.name!!,
                    item.title!!,
                    item.user!!.name!!,
                    Date(),
                    item.body!!
                )

        }}.toObservable()
    }
}