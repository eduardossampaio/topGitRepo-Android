package com.eduardossampaio.toprepos.features.list_repos.interactor

import com.eduardossampaio.toprepos.views.interactors.BaseInteractor
import com.eduardossampaio.toprepos.views.presenters.BasePresenter
import com.eduardossampaio.toprepos.features.list_repos.presenter.ShowRepositoriesPresenter
import com.eduardossampaio.toprepos.flow.github.GitRepositoriesFlow
import com.esampaio.core.models.Repo
import com.esampaio.core.usecases.repositories.list.ShowRepositoriesUseCase
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.PublishSubject

interface ListRepositoriesInteractor : BaseInteractor<Unit?,ShowRepositoriesPresenter> {
}

class ListRepositoriesInteractorImpl(
    private val flow: GitRepositoriesFlow,
    private val useCase: ShowRepositoriesUseCase
) : ListRepositoriesInteractor {

    private lateinit var presenter: ShowRepositoriesPresenter

    private var disposable: Disposable? = null

    private var onPageChangeDisposable: Disposable? = null
    private var onRepositoryDisposable: Disposable? = null

    override fun start(initParams: Unit?, with: ShowRepositoriesPresenter) {
        this.presenter = with
        presenter.showLoading()
        onPageChangeDisposable = presenter.onPageChanged.subscribe { page ->
            loadMore()
        }
        onRepositoryDisposable = presenter.onRepositoryClicked.subscribe { repo ->
            flow.detailRepo(presenter.getContext(), repo)
        }

        disposable = useCase.start(null).subscribe({ repositories ->
            presenter.showRepositories(repositories)
        }, { error ->
            presenter.showError(error)
        })
    }

    private  fun  loadMore(){
        useCase.loadMore()
    }

    override fun destroy() {
        onPageChangeDisposable?.dispose()
        onRepositoryDisposable?.dispose()
    }

}