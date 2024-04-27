package com.esampaio.core.presenters

interface BasePresenter {

    fun showLoading();

    fun hideLoading();

    fun showError(error: Throwable)
}