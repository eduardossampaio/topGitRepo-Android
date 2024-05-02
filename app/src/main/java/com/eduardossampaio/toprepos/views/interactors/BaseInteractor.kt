package com.eduardossampaio.toprepos.views.interactors

import com.eduardossampaio.toprepos.views.presenters.BasePresenter

interface  BaseInteractor<T, P : BasePresenter>{
//    fun bind(presenter: BasePresenter)

    fun start(initParams: T, with: P);

    fun destroy();
}