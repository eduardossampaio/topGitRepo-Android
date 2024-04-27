package com.esampaio.core.services.gitService


import com.esampaio.core.models.PullRequest
import com.esampaio.core.models.Repo
import com.esampaio.core.services.gitService.models.SearchQuery
import io.reactivex.rxjava3.core.Observable

interface GitApiService {

    fun listAllRepositories(page:Int, searchQuery:SearchQuery?): Observable<List<Repo>>

    fun listPullRequests(repo: Repo): Observable<List<PullRequest>>
}