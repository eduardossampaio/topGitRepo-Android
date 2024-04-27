package com.esampaio.core.presenters.repositories

import com.esampaio.core.models.Repo
import com.esampaio.core.presenters.BasePresenter
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer


interface ShowRepositoriesPresenter : BasePresenter {

    val onRepositoryClicked: Observable<Repo>

    val onPageChanged: Observable<Int>

    fun showRepositories(repo: List<Repo>)



}