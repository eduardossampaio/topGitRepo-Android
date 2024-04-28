package com.eduardossampaio.toprepos.features.list_repos.view

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.eduardossampaio.toprepos.R
import com.eduardossampaio.toprepos.views.custom.OnReachEndOfListListener
import com.eduardossampaio.toprepos.views.custom.ScrollableRecyclerView
import com.eduardossampaio.toprepos.features.list_repos.interactor.ListRepositoriesInteractor
import com.esampaio.core.models.Repo
import com.eduardossampaio.toprepos.features.list_repos.presenter.ShowRepositoriesPresenter
import com.eduardossampaio.toprepos.features.list_repos.view.adapter.ListRepositoriesRecyclerViewAdapter
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import org.koin.android.ext.android.inject

class ListRepositoriesActivity : AppCompatActivity(), ShowRepositoriesPresenter {
    private val interactor: ListRepositoriesInteractor by inject()

    private var onRepositoryClickedSubject =  PublishSubject.create<Repo>()
    private var onPageChangedSubject =  PublishSubject.create<Int>()

    override val onRepositoryClicked: Observable<Repo> = onRepositoryClickedSubject
    override val onPageChanged: Observable<Int> = onPageChangedSubject


    lateinit var loading:ProgressBar
    lateinit var repositoryList: ScrollableRecyclerView
    lateinit var adapter: ListRepositoriesRecyclerViewAdapter

    private var currentPage = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_repositories)
        setTitle(R.string.list_repo_title)

        bindViews();
        setupViews();

//        startKoin{
//            androidLogger()
//            androidContext(this@ListRepositoriesActivity)
//            modules(appModule)
//        }

        interactor.bind(this)
        interactor.start()


    }

    override fun onDestroy() {
        super.onDestroy()

    }

    private fun bindViews(){
        loading = findViewById(R.id.loading)
        repositoryList = findViewById(R.id.repoList);
    }
    private fun setupViews(){
        hideLoading()
        adapter = ListRepositoriesRecyclerViewAdapter(this){
            
        }
        repositoryList.adapter = adapter
        repositoryList.layoutManager = LinearLayoutManager(this)
        repositoryList.onReachEndOfListListener = object :OnReachEndOfListListener {
            override fun onReachEnd() {
                currentPage++
                onPageChangedSubject.onNext(currentPage);
            }
        }
    }

    override fun showRepositories(repo: List<Repo>) {
        hideLoading()
        adapter.appendItems(repo)
    }

    override fun showLoading() {
        loading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        loading.visibility = View.GONE
    }

    override fun showError(error: Throwable) {

    }

}

