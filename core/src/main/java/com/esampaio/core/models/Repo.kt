package com.esampaio.core.models

data class Repo(
    val id: Long,
    val name: String,
    val description: String,
    val authorName: String,
    val authorProfilePictureUrl: String,
    val starCount: Int,
    val forkCount: Long,
    val pullRequest:List<PullRequest>?
)
