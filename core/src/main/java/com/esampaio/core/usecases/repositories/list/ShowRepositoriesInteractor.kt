package com.esampaio.core.usecases.repositories.list

import com.esampaio.core.models.Repo
import com.esampaio.core.usecases.UseCaseInteractor
import io.reactivex.rxjava3.core.Observable

interface ShowRepositoriesInteractor : UseCaseInteractor {

    fun showRepositories(repositories: List<Repo>)

    val onPageChanged: Observable<Int>
}