package com.eduardossampaio.toprepos

import android.content.Context
import com.eduardossampaio.toprepos.features.detail_repo.interactor.ListPullRequestsInteractor
import com.eduardossampaio.toprepos.features.detail_repo.interactor.ListPullRequestsInteractorImpl
import com.eduardossampaio.toprepos.flow.github.GitRepositoriesFlow
import com.eduardossampaio.toprepos.impls.services.github.GithubApiService
import com.eduardossampaio.toprepos.features.list_repos.interactor.ListRepositoriesInteractor
import com.eduardossampaio.toprepos.features.list_repos.interactor.ListRepositoriesInteractorImpl
import com.eduardossampaio.toprepos.features.splash.interactor.SplashInteractor
import com.eduardossampaio.toprepos.features.splash.interactor.SplashInteractorImpl
import com.eduardossampaio.toprepos.flow.github.GitRepositoriesFlowImpl
import com.esampaio.core.services.gitService.GitApiService
import com.esampaio.core.usecases.repositories.list.ShowRepositoriesUseCase
import com.esampaio.core.usecases.repositories.pr.ListPRUseCase
import com.esampaio.core.usecases.repositories.pr.ListPRUseCaseImpl
import org.koin.dsl.module


val appModule = module {
    factory<GitApiService> { GithubApiService()  }
    factory { ShowRepositoriesUseCase(get<GitApiService>()) }
    factory<GitRepositoriesFlow> { GitRepositoriesFlowImpl() }
    factory<SplashInteractor> { (activityContext: Context) -> SplashInteractorImpl(activityContext )  }
    factory<ListRepositoriesInteractor> { ListRepositoriesInteractorImpl(get<GitRepositoriesFlow>(),get<ShowRepositoriesUseCase>()) }

    factory<ListPRUseCase> { ListPRUseCaseImpl(get<GitApiService>())  }
    factory<ListPullRequestsInteractor> { ListPullRequestsInteractorImpl(get<GitRepositoriesFlow>(),get<ListPRUseCase>())  }
}


