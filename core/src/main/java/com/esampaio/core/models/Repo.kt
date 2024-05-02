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


) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Repo

        if (id != other.id) return false
        if (name != other.name) return false
        if (description != other.description) return false
        if (authorName != other.authorName) return false
        if (authorProfilePictureUrl != other.authorProfilePictureUrl) return false
        if (starCount != other.starCount) return false
        if (forkCount != other.forkCount) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + authorName.hashCode()
        result = 31 * result + authorProfilePictureUrl.hashCode()
        result = 31 * result + starCount
        result = 31 * result + forkCount.hashCode()
        return result
    }
}
