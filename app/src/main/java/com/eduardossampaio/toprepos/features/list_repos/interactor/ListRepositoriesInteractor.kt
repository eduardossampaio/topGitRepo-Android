package com.eduardossampaio.toprepos.features.list_repos.interactor

import com.eduardossampaio.toprepos.views.interactors.BaseInteractor
import com.eduardossampaio.toprepos.views.presenters.BasePresenter
import com.eduardossampaio.toprepos.features.list_repos.presenter.ShowRepositoriesPresenter
import com.eduardossampaio.toprepos.flow.github.GitRepositoriesFlow
import com.esampaio.core.models.Repo
import com.esampaio.core.usecases.repositories.ShowRepositoriesInteractor
import com.esampaio.core.usecases.repositories.ShowRepositoriesUseCase
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.PublishSubject

interface ListRepositoriesInteractor : BaseInteractor {
}

class ListRepositoriesInteractorImpl(
    private val flow: GitRepositoriesFlow,
    private val useCase: ShowRepositoriesUseCase) : ListRepositoriesInteractor, ShowRepositoriesInteractor{

    private lateinit var presenter: ShowRepositoriesPresenter

    private val onPageChangedSubject =  PublishSubject.create<Int>()
    override val onPageChanged: Observable<Int> = onPageChangedSubject

    private var onPageChangeDisposable:Disposable? = null
    private var onRepositoryDisposable:Disposable? = null

    override fun bind(presenter: BasePresenter) {
        this.presenter = presenter as ShowRepositoriesPresenter
    }

    override fun start() {
        presenter.showLoading()
        onPageChangeDisposable = presenter.onPageChanged.subscribe {page ->
            onPageChangedSubject.onNext(page);
        }
        onRepositoryDisposable = presenter.onRepositoryClicked.subscribe { repo ->
            flow.detailRepo(presenter.getContext(), repo)
        }
        useCase.showRepositoriesInteractor = this
        useCase.start();
    }

    override fun destroy() {
        onPageChangeDisposable?.dispose()
        onRepositoryDisposable?.dispose()
    }

    override fun showRepositories(repositories: List<Repo>) {
       presenter.showRepositories(repositories)
    }

    override fun notifyError(error: Throwable) {
        presenter.showError(error)
    }

}