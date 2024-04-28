package com.eduardossampaio.toprepos.features.detail_repo.view

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import com.eduardossampaio.toprepos.databinding.ActivityDetailRepoBinding
import com.eduardossampaio.toprepos.features.detail_repo.interactor.ListPullRequestsInteractor
import com.eduardossampaio.toprepos.features.detail_repo.presenter.ListPRPresenter
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

    val interactor: ListPullRequestsInteractor by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        views = ActivityDetailRepoBinding.inflate(layoutInflater)
        setContentView(views.root)

        val repoDTO:RepoDTO? = IntentCompat.getParcelableExtra(intent, PARAM_REPO, RepoDTO::class.java)

        if (repoDTO != null) {
            repo  = repoDTO.toRepo()
        }

    }

    override fun onResume() {
        super.onResume()
        interactor.bind(this)
        interactor.start(repo)
    }

    override fun showPullRequestList(pullRequestList: List<PullRequest>) {

    }

    override fun getContext(): Context {
        return this;
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showError(error: Throwable) {

    }
}