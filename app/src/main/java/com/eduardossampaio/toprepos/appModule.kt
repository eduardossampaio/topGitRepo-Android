package com.eduardossampaio.toprepos

import com.eduardossampaio.toprepos.flow.Flow
import com.eduardossampaio.toprepos.impls.flows.github.GitRepositoriesFlow
import com.eduardossampaio.toprepos.views.interactors.SplashInteractor
import com.eduardossampaio.toprepos.views.interactors.SplashInteractorImpl
import org.koin.dsl.module


val appModule = module {
//    factory { ProductsListAdapter(context = get()) }
    factory { GitRepositoriesFlow() as Flow }
    factory { SplashInteractorImpl(context = get()) as SplashInteractor }
}


