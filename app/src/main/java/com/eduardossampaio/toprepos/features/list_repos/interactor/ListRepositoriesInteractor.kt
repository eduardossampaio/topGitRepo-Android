package com.eduardossampaio.toprepos.features.list_repos.interactor

import com.eduardossampaio.toprepos.views.interactors.BaseInteractor
import com.eduardossampaio.toprepos.views.presenters.BasePresenter
import com.eduardossampaio.toprepos.features.list_repos.presenter.ShowRepositoriesPresenter
import com.esampaio.core.models.Repo
import com.esampaio.core.usecases.repositories.ShowRepositoriesInteractor
import com.esampaio.core.usecases.repositories.ShowRepositoriesUseCase
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

interface ListRepositoriesInteractor : BaseInteractor {
}

class ListRepositoriesInteractorImpl(private val useCase: ShowRepositoriesUseCase) : ListRepositoriesInteractor, ShowRepositoriesInteractor{
    private lateinit var presenter: ShowRepositoriesPresenter

    private val onPageChangedSubject =  PublishSubject.create<Int>()
    override val onPageChanged: Observable<Int> = onPageChangedSubject

    override fun bind(presenter: BasePresenter) {
        this.presenter = presenter as ShowRepositoriesPresenter
    }

    override fun start() {
        val onPageChangeDisposable = presenter.onPageChanged.subscribe {page ->
            onPageChangedSubject.onNext(page);
        }
        useCase.showRepositoriesInteractor = this
        useCase.start();
    }

    override fun showRepositories(repositories: List<Repo>) {
       presenter.showRepositories(repositories)
    }





    override fun notifyError(error: Throwable) {
        presenter.showError(error)
    }

}