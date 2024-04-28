package com.eduardossampaio.toprepos.views.interactors

import com.eduardossampaio.toprepos.views.presenters.BasePresenter

interface  BaseInteractor<T>{
    fun bind(presenter: BasePresenter)

    fun start(initParams: T);

    fun destroy();
}