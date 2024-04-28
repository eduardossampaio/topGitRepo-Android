package com.eduardossampaio.toprepos.views.presenters

import android.content.Context

interface BasePresenter {

    fun getContext(): Context
    fun showLoading();

    fun hideLoading();

    fun showError(error: Throwable)
}