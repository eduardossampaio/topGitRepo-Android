package com.eduardossampaio.toprepos.flow.github.dto

import android.os.Parcelable
import com.esampaio.core.models.Repo
import kotlinx.parcelize.Parcelize


@Parcelize
data class RepoDTO(
    val id: Long,
    val name: String,
    val description: String,
    val authorName: String,
    val authorProfilePictureUrl: String,
    val starCount: Int,
    val forkCount: Long,
): Parcelable {
    constructor(repo: Repo):this(
        repo.id,
        repo.name,
        repo.description,
        repo.authorName,
        repo.authorProfilePictureUrl,
        repo.starCount,
        repo.forkCount)

    fun toRepo():Repo{
        return Repo(
            id,
            name,
            description,
            authorName,
            authorProfilePictureUrl,
            starCount,
            forkCount,
            emptyList()
        )
    }
}
