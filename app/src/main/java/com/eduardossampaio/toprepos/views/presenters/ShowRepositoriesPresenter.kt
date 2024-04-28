package com.eduardossampaio.toprepos.views.presenters

import com.esampaio.core.models.Repo
import com.eduardossampaio.toprepos.views.presenters.BasePresenter
import io.reactivex.rxjava3.core.Observable


interface ShowRepositoriesPresenter : com.eduardossampaio.toprepos.views.presenters.BasePresenter {

    val onRepositoryClicked: Observable<Repo>

    val onPageChanged: Observable<Int>

    fun showRepositories(repo: List<Repo>)



}