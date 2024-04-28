package com.eduardossampaio.toprepos.features.detail_repo.presenter

import com.eduardossampaio.toprepos.views.presenters.BasePresenter
import com.esampaio.core.models.PullRequest

interface ListPRPresenter : BasePresenter {

    fun showPullRequestList(pullRequestList: List<PullRequest>)
}