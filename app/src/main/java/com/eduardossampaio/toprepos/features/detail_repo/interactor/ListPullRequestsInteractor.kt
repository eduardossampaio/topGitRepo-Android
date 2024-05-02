package com.eduardossampaio.toprepos.features.detail_repo.interactor

import com.eduardossampaio.toprepos.features.detail_repo.presenter.ListPRPresenter
import com.eduardossampaio.toprepos.flow.github.GitRepositoriesFlow
import com.eduardossampaio.toprepos.views.interactors.BaseInteractor
import com.eduardossampaio.toprepos.views.presenters.BasePresenter
import com.esampaio.core.models.PullRequest
import com.esampaio.core.models.Repo
import com.esampaio.core.usecases.repositories.pr.ListPRUseCase
import io.reactivex.rxjava3.disposables.Disposable

interface ListPullRequestsInteractor : BaseInteractor<Repo,ListPRPresenter>{
}
class ListPullRequestsInteractorImpl(private val flow: GitRepositoriesFlow,
                                     private val useCase: ListPRUseCase) : ListPullRequestsInteractor {

    lateinit var presenter: ListPRPresenter
    var subscribe: Disposable? = null


    override fun start(initParams: Repo, with: ListPRPresenter) {
        presenter = with
        presenter.showLoading()
        subscribe = useCase.start(initParams).subscribe(
            /* onNext = */ { prList ->
                presenter.showPullRequestList(prList)
            },
            /* onError = */ {

                presenter.showError((it))
            }
        )
    }

    override fun destroy() {
        subscribe?.dispose()
    }

}
