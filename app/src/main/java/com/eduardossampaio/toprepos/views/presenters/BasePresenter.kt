package com.eduardossampaio.toprepos.views.presenters

interface BasePresenter {

    fun showLoading();

    fun hideLoading();

    fun showError(error: Throwable)
}