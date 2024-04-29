package com.eduardossampaio.toprepos.features.detail_repo.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.eduardossampaio.toprepos.databinding.ActivityDetailRepoBinding
import com.eduardossampaio.toprepos.features.detail_repo.interactor.ListPullRequestsInteractor
import com.eduardossampaio.toprepos.features.detail_repo.presenter.ListPRPresenter
import com.eduardossampaio.toprepos.features.detail_repo.view.adapter.ListPullRequestRecyclerViewAdapter
import com.eduardossampaio.toprepos.flow.github.dto.RepoDTO
import com.esampaio.core.models.PullRequest
import com.esampaio.core.models.Repo
import com.esampaio.core.usecases.repositories.pr.ListPRUseCaseInteractor
import org.koin.android.ext.android.inject


class DetailRepoActivity : AppCompatActivity(), ListPRPresenter {
    companion object{
        const val PARAM_REPO = "DetailRepoActivity.PARAM_REPO"
    }

    lateinit var views: ActivityDetailRepoBinding
    lateinit var repo:Repo

    lateinit var listPullRequestsAdapter : ListPullRequestRecyclerViewAdapter
    val interactor: ListPullRequestsInteractor by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        views = ActivityDetailRepoBinding.inflate(layoutInflater)
        setContentView(views.root)

        val repoDTO:RepoDTO? = IntentCompat.getParcelableExtra(intent, PARAM_REPO, RepoDTO::class.java)

        if (repoDTO != null) {
            repo  = repoDTO.toRepo()
        }
        setTitle(repo.name)
        listPullRequestsAdapter = ListPullRequestRecyclerViewAdapter(this)
        views.pullrequestList.layoutManager = LinearLayoutManager(this)
        views.pullrequestList.adapter = listPullRequestsAdapter

    }

    override fun onResume() {
        super.onResume()
        interactor.bind(this)
        interactor.start(repo)
    }

    override fun showPullRequestList(pullRequestList: List<PullRequest>) {
        listPullRequestsAdapter.setItems(pullRequestList);
    }

    override fun getContext(): Context {
        return this;
    }

    override fun showLoading() {
        views.loading.visibility = View.VISIBLE
        views.showError.visibility = View.GONE
        views.pullrequestList.visibility = View.GONE
    }

    override fun hideLoading() {
        views.loading.visibility = View.GONE

    }

    override fun showError(error: Throwable) {
        hideLoading()
        views.showError.visibility = View.VISIBLE
        views.pullrequestList.visibility = View.GONE
    }
}