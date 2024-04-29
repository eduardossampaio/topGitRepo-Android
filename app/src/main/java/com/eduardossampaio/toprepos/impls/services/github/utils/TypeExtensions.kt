package com.eduardossampaio.toprepos.impls.services.github.utils

import com.eduardossampaio.toprepos.impls.services.github.models.GithubListPullRequestsResponse
import com.eduardossampaio.toprepos.impls.services.github.models.GithubListRepoResponse
import com.eduardossampaio.toprepos.impls.services.github.models.Item
import com.esampaio.core.models.PullRequest
import com.esampaio.core.models.Repo

fun GithubListPullRequestsResponse.parse(): PullRequest {
    return PullRequest(
        id ?: 0,
        repoItem?.name ?: "",
        title ?: "",
        user?.name?: "",
        user?.avatarUrl,
        createdAt,
        body ?: "",
    )
}

fun Item.parse(): Repo{
    return Repo(
        id ?: 0L,
        name ?: "",
        description ?: "",
        owner?.login ?: "",
        owner?.avatarUrl ?: "",
        stargazersCount?.toInt()  ?: 0,
        forksCount?.toLong() ?: 0, emptyList())
}