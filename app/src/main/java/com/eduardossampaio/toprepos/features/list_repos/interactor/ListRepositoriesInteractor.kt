package com.eduardossampaio.toprepos.features.list_repos.interactor

import android.content.Context
import android.content.Intent
import com.eduardossampaio.toprepos.features.detail_repo.DetailRepoActivity
import com.eduardossampaio.toprepos.views.interactors.BaseInteractor
import com.eduardossampaio.toprepos.views.presenters.BasePresenter
import com.eduardossampaio.toprepos.features.list_repos.presenter.ShowRepositoriesPresenter
import com.esampaio.core.models.Repo
import com.esampaio.core.usecases.repositories.ShowRepositoriesInteractor
import com.esampaio.core.usecases.repositories.ShowRepositoriesUseCase
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.PublishSubject

interface ListRepositoriesInteractor : BaseInteractor {
}

class ListRepositoriesInteractorImpl(
    private val context: Context,
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
//            val intent = Intent(context,DetailRepoActivity::class.java)
//            context.startActivity(intent)
        }
        useCase.showRepositoriesInteractor = this
        useCase.start();
    }

    override fun destroy() {
        onPageChangeDisposable?.dispose()
    }

    override fun showRepositories(repositories: List<Repo>) {
       presenter.showRepositories(repositories)
    }

    override fun notifyError(error: Throwable) {
        presenter.showError(error)
    }

}