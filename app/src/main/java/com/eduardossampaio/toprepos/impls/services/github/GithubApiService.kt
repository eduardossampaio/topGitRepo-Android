package com.eduardossampaio.toprepos.impls.services.github


import android.util.Log
import com.esampaio.core.models.PullRequest
import com.esampaio.core.models.Repo
import com.esampaio.core.services.gitService.GitApiService
import com.esampaio.core.services.gitService.models.SearchQuery
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.toObservable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
//import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.jackson.JacksonConverterFactory


class GithubApiService : GitApiService {
    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
//        .addConverterFactory(GsonConverterFactory.create())
        .addConverterFactory(JacksonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    override fun listAllRepositories(page: Int, searchQuery: SearchQuery?): Observable<List<Repo>> {
                val service =  this.retrofit.create(GithubApiServiceRetrofit::class.java)


        val observable = service.listRepos("language:Java",searchQuery?.sortBy.toString(),page);
        return observable.map { it.items!!.map {
                item -> Repo(
            item.id!!.toLong(), item.name!!, item.description!!, item.owner!!.login!!,item.owner!!.avatarUrl!!,
            item.stargazersCount!!.toInt(),item.forksCount!!.toLong(), emptyList())

//            val repo1 = Repo(1,"teste","tteste","teste",
//                "teste",12,123, emptyList()
//            )

          }}.toObservable()
    }
     fun listAllRepositories_test(page: Int, searchQuery: SearchQuery?): Observable<List<Repo>> {

        val repo1 = Repo(
            132464395
            ,"JavaGuide",
            "「Java学习+面试指南」一份涵盖大部分 Java 程序员所需要掌握的核心知识。准备 Java 面试，首选 JavaGuide！",
            "Snailclimb",
            "https://avatars.githubusercontent.com/u/29880145?v=4" ,
            143208,
            45186,
            emptyList()
        )
        val repo2 = Repo(
            206462776,
            "GitHub-Chinese-Top-Charts",
            ":cn: GitHub中文排行榜，各语言分设「软件 | 资料」榜单，精准定位中文好项目。各取所需，高效学习。",
            "GrowingGit",
            "https://avatars.githubusercontent.com/u/21018904?v=4",
            8893,
            12086,
            emptyList()
        )
        val repo3 = Repo(
            22790488,
            "java-design-patterns",
            "Design patterns implemented in Java",
            "iluwatar",
            "https://avatars.githubusercontent.com/u/582346?v=4",
            86472,
            25897,
            emptyList()
        )
        val repo4 = Repo(
            127988011,
            "mall",
            "mall项目是一套电商系统，包括前台商城系统及后台管理系统，基于SpringBoot+MyBatis实现，采用Docker容器化部署。 前台商城系统包含首页门户、商品推荐、商品搜索、商品展示、购物车、订单流程、会员中心、客户服务、帮助中心等模块。 后台管理系统包含商品管理、订单管理、会员管理、促销管理、运营管理、内容管理、统计报表、财务管理、权限管理、设置等模块。",
            "macrozheng",
            "https://avatars.githubusercontent.com/u/15903809?v=4",
            75430,
            28287,
            emptyList()

        )

        //return listOf(listOf(repo1,repo2,repo3,repo4)).toObservable()
        return  Observable.just<List<Repo>>(listOf(repo1,repo2,repo3,repo4,repo1,repo2,repo3,repo4,repo1,repo2,repo3,repo4))
    }

    fun teste(){
        val service =  this.retrofit.create(GithubApiServiceRetrofit::class.java)
        val obs = service.listRepos("language:Java","stars",1)
       .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                /* onNext = */ {
                    Log.d("SplashActivity", "list all repos")
                },
                /* onError = */ {
                    it.printStackTrace()
                    it.message?.let { it1 -> Log.e("SplashActivity", it1) }
                }
            )
    }


//    override fun listAllRepositories(): Observable<List<Repo>> {
////        val service =  this.retrofit.create(GithubApiServiceRetrofit::class.java)
////        val observable = service.listRepos("language:Java","stars",1);
////        return observable.map { it.items!!.map {
//////                item -> Repo(
//////            item.id!!.toLong(), item.name!!, item.description!!, item.owner!!.login!!,item.owner!!.avatarUrl!!,
//////            item.stargazersCount!!.toInt(),item.forksCount!!.toLong(), emptyList()
////
//            val repo1 = Repo(1,"teste","tteste","teste",
//                "teste",12,123, emptyList()
//            )
////
////          }}.toObservable()
//
//        return listOf(listOf(repo1)).toObservable();
//    }

//    val id: Double,
//    val name: String,
//    val description: String,
//    val authorName: String,
//    val authorProfilePictureUrl: String,
//    val starCount: Int,
//    val forkCount: Int,
//    val pullRequest:List<PullRequest>?

//    {
//        "id": 132464395,
//        "node_id": "MDEwOlJlcG9zaXRvcnkxMzI0NjQzOTU=",
//        "name": "JavaGuide",
//        "full_name": "Snailclimb/JavaGuide",
//        "private": false,
//        "owner": {
//        "login": "Snailclimb",
//        "id": 29880145,
//        "node_id": "MDQ6VXNlcjI5ODgwMTQ1",
//        "avatar_url": "https://avatars.githubusercontent.com/u/29880145?v=4",
//        "gravatar_id": "",
//        "url": "https://api.github.com/users/Snailclimb",
//        "html_url": "https://github.com/Snailclimb",
//        "followers_url": "https://api.github.com/users/Snailclimb/followers",
//        "following_url": "https://api.github.com/users/Snailclimb/following{/other_user}",
//        "gists_url": "https://api.github.com/users/Snailclimb/gists{/gist_id}",
//        "starred_url": "https://api.github.com/users/Snailclimb/starred{/owner}{/repo}",
//        "subscriptions_url": "https://api.github.com/users/Snailclimb/subscriptions",
//        "organizations_url": "https://api.github.com/users/Snailclimb/orgs",
//        "repos_url": "https://api.github.com/users/Snailclimb/repos",
//        "events_url": "https://api.github.com/users/Snailclimb/events{/privacy}",
//        "received_events_url": "https://api.github.com/users/Snailclimb/received_events",
//        "type": "User",
//        "site_admin": false
//    },
//        "html_url": "https://github.com/Snailclimb/JavaGuide",
//        "description": "「Java学习+面试指南」一份涵盖大部分 Java 程序员所需要掌握的核心知识。准备 Java 面试，首选 JavaGuide！",
//        "fork": false,
//        "url": "https://api.github.com/repos/Snailclimb/JavaGuide",
//        "forks_url": "https://api.github.com/repos/Snailclimb/JavaGuide/forks",
//        "keys_url": "https://api.github.com/repos/Snailclimb/JavaGuide/keys{/key_id}",
//        "collaborators_url": "https://api.github.com/repos/Snailclimb/JavaGuide/collaborators{/collaborator}",
//        "teams_url": "https://api.github.com/repos/Snailclimb/JavaGuide/teams",
//        "hooks_url": "https://api.github.com/repos/Snailclimb/JavaGuide/hooks",
//        "issue_events_url": "https://api.github.com/repos/Snailclimb/JavaGuide/issues/events{/number}",
//        "events_url": "https://api.github.com/repos/Snailclimb/JavaGuide/events",
//        "assignees_url": "https://api.github.com/repos/Snailclimb/JavaGuide/assignees{/user}",
//        "branches_url": "https://api.github.com/repos/Snailclimb/JavaGuide/branches{/branch}",
//        "tags_url": "https://api.github.com/repos/Snailclimb/JavaGuide/tags",
//        "blobs_url": "https://api.github.com/repos/Snailclimb/JavaGuide/git/blobs{/sha}",
//        "git_tags_url": "https://api.github.com/repos/Snailclimb/JavaGuide/git/tags{/sha}",
//        "git_refs_url": "https://api.github.com/repos/Snailclimb/JavaGuide/git/refs{/sha}",
//        "trees_url": "https://api.github.com/repos/Snailclimb/JavaGuide/git/trees{/sha}",
//        "statuses_url": "https://api.github.com/repos/Snailclimb/JavaGuide/statuses/{sha}",
//        "languages_url": "https://api.github.com/repos/Snailclimb/JavaGuide/languages",
//        "stargazers_url": "https://api.github.com/repos/Snailclimb/JavaGuide/stargazers",
//        "contributors_url": "https://api.github.com/repos/Snailclimb/JavaGuide/contributors",
//        "subscribers_url": "https://api.github.com/repos/Snailclimb/JavaGuide/subscribers",
//        "subscription_url": "https://api.github.com/repos/Snailclimb/JavaGuide/subscription",
//        "commits_url": "https://api.github.com/repos/Snailclimb/JavaGuide/commits{/sha}",
//        "git_commits_url": "https://api.github.com/repos/Snailclimb/JavaGuide/git/commits{/sha}",
//        "comments_url": "https://api.github.com/repos/Snailclimb/JavaGuide/comments{/number}",
//        "issue_comment_url": "https://api.github.com/repos/Snailclimb/JavaGuide/issues/comments{/number}",
//        "contents_url": "https://api.github.com/repos/Snailclimb/JavaGuide/contents/{+path}",
//        "compare_url": "https://api.github.com/repos/Snailclimb/JavaGuide/compare/{base}...{head}",
//        "merges_url": "https://api.github.com/repos/Snailclimb/JavaGuide/merges",
//        "archive_url": "https://api.github.com/repos/Snailclimb/JavaGuide/{archive_format}{/ref}",
//        "downloads_url": "https://api.github.com/repos/Snailclimb/JavaGuide/downloads",
//        "issues_url": "https://api.github.com/repos/Snailclimb/JavaGuide/issues{/number}",
//        "pulls_url": "https://api.github.com/repos/Snailclimb/JavaGuide/pulls{/number}",
//        "milestones_url": "https://api.github.com/repos/Snailclimb/JavaGuide/milestones{/number}",
//        "notifications_url": "https://api.github.com/repos/Snailclimb/JavaGuide/notifications{?since,all,participating}",
//        "labels_url": "https://api.github.com/repos/Snailclimb/JavaGuide/labels{/name}",
//        "releases_url": "https://api.github.com/repos/Snailclimb/JavaGuide/releases{/id}",
//        "deployments_url": "https://api.github.com/repos/Snailclimb/JavaGuide/deployments",
//        "created_at": "2018-05-07T13:27:00Z",
//        "updated_at": "2024-04-27T11:29:08Z",
//        "pushed_at": "2024-04-27T01:11:09Z",
//        "git_url": "git://github.com/Snailclimb/JavaGuide.git",
//        "ssh_url": "git@github.com:Snailclimb/JavaGuide.git",
//        "clone_url": "https://github.com/Snailclimb/JavaGuide.git",
//        "svn_url": "https://github.com/Snailclimb/JavaGuide",
//        "homepage": "https://javaguide.cn",
//        "size": 175722,
//        "stargazers_count": 143201,
//        "watchers_count": 143201,
//        "language": "Java",
//        "has_issues": true,
//        "has_projects": true,
//        "has_downloads": true,
//        "has_wiki": true,
//        "has_pages": false,
//        "has_discussions": true,
//        "forks_count": 45185,
//        "mirror_url": null,
//        "archived": false,
//        "disabled": false,
//        "open_issues_count": 59,
//        "license": {
//        "key": "apache-2.0",
//        "name": "Apache License 2.0",
//        "spdx_id": "Apache-2.0",
//        "url": "https://api.github.com/licenses/apache-2.0",
//        "node_id": "MDc6TGljZW5zZTI="
//    },
//        "allow_forking": true,
//        "is_template": false,
//        "web_commit_signoff_required": false,
//        "topics": [
//        "algorithms",
//        "interview",
//        "java",
//        "jvm",
//        "mysql",
//        "redis",
//        "spring",
//        "system",
//        "system-design",
//        "zookeeper"
//        ],
//        "visibility": "public",
//        "forks": 45185,
//        "open_issues": 59,
//        "watchers": 143201,
//        "default_branch": "main",
//        "score": 1.0
//    },

    override fun listPullRequests(repo: Repo): Observable<List<PullRequest>> {
        TODO("Not yet implemented")
    }
}