package com.eduardossampaio.toprepos.impls.services.github

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class GithubListPullRequestsResponse(
    @JsonProperty("id")
    val id: Long?,

    @JsonProperty("title")
    val title: String?,

    @JsonProperty("repo")
    val repoItem: RepoItem?,

    @JsonProperty("user")
    val user: UserItem?,

    @JsonProperty("body")
    val body: String?,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class RepoItem(
    @JsonProperty("name")
    val name: String?
)
@JsonIgnoreProperties(ignoreUnknown = true)
data class UserItem(
    @JsonProperty("login")
    val name: String?
)
//"user": {
//    "login": "mangyimang",
//@JsonIgnoreProperties(ignoreUnknown = true)
//data class Item(
//    @JsonProperty("id")
//    val id: Long?,
//}