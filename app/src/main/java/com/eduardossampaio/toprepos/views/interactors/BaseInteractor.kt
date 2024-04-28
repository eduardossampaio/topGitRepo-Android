package com.eduardossampaio.toprepos.views.interactors

import com.eduardossampaio.toprepos.views.presenters.BasePresenter

interface BaseInteractor {
    fun bind(presenter: BasePresenter)

    fun start();

    fun destroy();
}