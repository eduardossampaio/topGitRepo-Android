package com.eduardossampaio.toprepos.impls.services.github


import com.eduardossampaio.toprepos.impls.services.github.models.GithubListPullRequestsResponse
import com.eduardossampaio.toprepos.impls.services.github.models.GithubListRepoResponse
//import com.eduardossampaio.toprepos.core.services.impls.github.GithubRepo
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiServiceRetrofit {

      @GET("search/repositories")
      fun listRepos(@Query("q") searchParams:String,@Query("sort") sort:String, @Query("page") page:Int):Flowable<GithubListRepoResponse>

      @GET("repos/{create_by}/{repo_name}/pulls")
      fun listPullRequests(@Path("create_by") createdBy:String,@Path("repo_name")repoName:String):Flowable<List<GithubListPullRequestsResponse>>
}
