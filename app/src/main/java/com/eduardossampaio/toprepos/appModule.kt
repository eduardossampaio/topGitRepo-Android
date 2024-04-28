package com.eduardossampaio.toprepos

import android.content.Context
import com.eduardossampaio.toprepos.flow.Flow
import com.eduardossampaio.toprepos.flow.github.GitRepositoriesFlow
import com.eduardossampaio.toprepos.impls.services.github.GithubApiService
import com.eduardossampaio.toprepos.features.list_repos.interactor.ListRepositoriesInteractor
import com.eduardossampaio.toprepos.features.list_repos.interactor.ListRepositoriesInteractorImpl
import com.eduardossampaio.toprepos.features.splash.interactor.SplashInteractor
import com.eduardossampaio.toprepos.features.splash.interactor.SplashInteractorImpl
import com.esampaio.core.services.gitService.GitApiService
import com.esampaio.core.usecases.repositories.ShowRepositoriesUseCase
import org.koin.dsl.module


val appModule = module {
//    factory { ProductsListAdapter(context = get()) }
    factory<GitApiService> { GithubApiService()  }
    factory { ShowRepositoriesUseCase(get<GitApiService>()) }
    factory { GitRepositoriesFlow() as Flow }
    factory<SplashInteractor> { (activityContext: Context) -> SplashInteractorImpl(activityContext )  }
//    factory<ListRepositoriesInteractor> { ListRepositoriesInteractorImpl(get<ShowRepositoriesUseCase>()) }
    factory<ListRepositoriesInteractor> { (activityContext: Context) -> ListRepositoriesInteractorImpl(activityContext,get<ShowRepositoriesUseCase>()) }


//    factory { (activityContext: Context) ->
//        MyClass(activityContext)
//    }
}


