package com.eduardossampaio.toprepos.impls.services.github


//import retrofit2.converter.gson.GsonConverterFactory
import com.eduardossampaio.toprepos.impls.services.github.utils.parse
import com.esampaio.core.models.PullRequest
import com.esampaio.core.models.Repo
import com.esampaio.core.services.gitService.GitApiService
import com.esampaio.core.services.gitService.models.SearchQuery
import io.reactivex.rxjava3.core.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.Date
import java.util.concurrent.TimeUnit


class GithubApiService : GitApiService {
    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .client(provideOkHttpClient())
        .addConverterFactory(JacksonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    private fun provideOkHttpClient(): OkHttpClient {
        //this is the part where you will see all the logs of retrofit requests
        //and responses
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient().newBuilder()
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
            .readTimeout(5000, TimeUnit.MILLISECONDS)
            .addInterceptor(logging)
            .build()
    }
    override fun listAllRepositories(page: Int, searchQuery: SearchQuery?): Observable<List<Repo>> {
        val service =  this.retrofit.create(GithubApiServiceRetrofit::class.java)
        val language = searchQuery?.languages.toString() ?: ""
        val observable = service.listRepos("language:${language}",searchQuery?.sortBy.toString(),page);
        return observable.map { it.items.map {item ->
            item.parse()

          }}.toObservable()
    }
    override fun listPullRequests(repo: Repo): Observable<List<PullRequest>> {
        val service =  this.retrofit.create(GithubApiServiceRetrofit::class.java)

        val observable = service.listPullRequests(repo.authorName,repo.name);
        return observable.map { it.map { item -> item.parse() }}.toObservable()
    }
}