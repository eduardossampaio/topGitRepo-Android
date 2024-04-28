package com.eduardossampaio.toprepos.features.list_repos.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.eduardossampaio.toprepos.R
import com.eduardossampaio.toprepos.databinding.ActivityListRepositoriesBinding
import com.eduardossampaio.toprepos.views.custom.OnReachEndOfListListener
import com.eduardossampaio.toprepos.views.custom.ScrollableRecyclerView
import com.eduardossampaio.toprepos.features.list_repos.interactor.ListRepositoriesInteractor
import com.esampaio.core.models.Repo
import com.eduardossampaio.toprepos.features.list_repos.presenter.ShowRepositoriesPresenter
import com.eduardossampaio.toprepos.features.list_repos.view.adapter.ListRepositoriesRecyclerViewAdapter
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class ListRepositoriesActivity : AppCompatActivity(), ShowRepositoriesPresenter {
    private val interactor: ListRepositoriesInteractor by inject ()


    private var onRepositoryClickedSubject =  PublishSubject.create<Repo>()
    private var onPageChangedSubject =  PublishSubject.create<Int>()

    override val onRepositoryClicked: Observable<Repo> = onRepositoryClickedSubject
    override val onPageChanged: Observable<Int> = onPageChangedSubject

    lateinit var views:ActivityListRepositoriesBinding
    lateinit var adapter: ListRepositoriesRecyclerViewAdapter

    private var currentPage = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        views = ActivityListRepositoriesBinding.inflate(layoutInflater)
        setContentView(views.root)

        setupViews();

        interactor.bind(this)
        interactor.start()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        interactor.destroy()
    }

    private fun setupViews(){
        setTitle(R.string.list_repo_title)
        hideLoading()
        adapter = ListRepositoriesRecyclerViewAdapter(this){repo ->
            onRepositoryClickedSubject.onNext(repo)
        }
        views.repoList.adapter = adapter
        views.repoList.layoutManager = LinearLayoutManager(this)
        views.repoList.onReachEndOfListListener = object :OnReachEndOfListListener {
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
        views.loading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        views.loading.visibility = View.GONE
    }

    override fun showError(error: Throwable) {

    }

    override fun getContext(): Context {
        return this;
    }

}

