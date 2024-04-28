package com.esampaio.core.usecases.repositories.pr

import com.esampaio.core.models.PullRequest
import com.esampaio.core.usecases.UseCaseInteractor

interface ListPRUseCaseInteractor : UseCaseInteractor {

    fun showPullRequestList(prList: List<PullRequest>)
}