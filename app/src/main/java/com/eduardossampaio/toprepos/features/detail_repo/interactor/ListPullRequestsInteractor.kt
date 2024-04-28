package com.eduardossampaio.toprepos.features.detail_repo.interactor

import com.eduardossampaio.toprepos.features.detail_repo.presenter.ListPRPresenter
import com.eduardossampaio.toprepos.flow.github.GitRepositoriesFlow
import com.eduardossampaio.toprepos.views.interactors.BaseInteractor
import com.eduardossampaio.toprepos.views.presenters.BasePresenter
import com.esampaio.core.models.PullRequest
import com.esampaio.core.models.Repo
import com.esampaio.core.usecases.repositories.pr.ListPRUseCase
import com.esampaio.core.usecases.repositories.pr.ListPRUseCaseInteractor

interface ListPullRequestsInteractor : BaseInteractor<Repo>{
}
class ListPullRequestsInteractorImpl(private val flow: GitRepositoriesFlow,
                                     private val useCase: ListPRUseCase) : ListPullRequestsInteractor,ListPRUseCaseInteractor {

    lateinit var presenter: ListPRPresenter

    override fun bind(presenter: BasePresenter) {
        this.presenter = presenter as ListPRPresenter
    }

    override fun start(initParams: Repo) {
        useCase.interactor = this
        useCase.start(initParams)
    }

    override fun destroy() {

    }

    override fun showPullRequestList(prList: List<PullRequest>) {
        presenter.showPullRequestList(prList)
    }

    override fun notifyError(error: Throwable) {
        presenter.showError(error);
    }

}
