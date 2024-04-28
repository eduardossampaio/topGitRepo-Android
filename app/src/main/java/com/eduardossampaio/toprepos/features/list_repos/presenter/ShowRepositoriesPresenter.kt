package com.eduardossampaio.toprepos.features.list_repos.presenter

import com.esampaio.core.models.Repo
import com.eduardossampaio.toprepos.views.presenters.BasePresenter
import io.reactivex.rxjava3.core.Observable


interface ShowRepositoriesPresenter : BasePresenter {

    val onRepositoryClicked: Observable<Repo>

    val onPageChanged: Observable<Int>

    fun showRepositories(repo: List<Repo>)



}