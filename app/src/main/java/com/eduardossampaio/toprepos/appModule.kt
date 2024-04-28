package com.eduardossampaio.toprepos

import com.eduardossampaio.toprepos.flow.Flow
import com.eduardossampaio.toprepos.impls.flows.github.GitRepositoriesFlow
import com.eduardossampaio.toprepos.impls.services.github.GithubApiService
import com.eduardossampaio.toprepos.views.interactors.ListRepositoriesInteractor
import com.eduardossampaio.toprepos.views.interactors.ListRepositoriesInteractorImpl
import com.eduardossampaio.toprepos.views.interactors.SplashInteractor
import com.eduardossampaio.toprepos.views.interactors.SplashInteractorImpl
import com.esampaio.core.services.gitService.GitApiService
import com.esampaio.core.usecases.repositories.ShowRepositoriesUseCase
import org.koin.dsl.module


val appModule = module {
//    factory { ProductsListAdapter(context = get()) }
    factory<GitApiService> { GithubApiService()  }
    factory { ShowRepositoriesUseCase(get<GitApiService>()) }
    factory { GitRepositoriesFlow() as Flow }
    factory { SplashInteractorImpl(context = get()) as SplashInteractor }
    factory { ListRepositoriesInteractorImpl(get<ShowRepositoriesUseCase>()) as ListRepositoriesInteractor }
}


