package com.eduardossampaio.toprepos.flow.github

import android.content.Context
import android.content.Intent
import com.eduardossampaio.toprepos.features.detail_repo.view.DetailRepoActivity
import com.eduardossampaio.toprepos.features.list_repos.view.ListRepositoriesActivity
import com.eduardossampaio.toprepos.flow.github.dto.RepoDTO
import com.esampaio.core.models.Repo


class GitRepositoriesFlowImpl: GitRepositoriesFlow{


    override fun start(context: Context) {
        val intent = Intent(context, ListRepositoriesActivity::class.java)
        context.startActivity(intent);
    }

    override fun detailRepo(context: Context,repo: Repo) {
        val intent = Intent(context, DetailRepoActivity::class.java)
        intent.putExtra("example",RepoDTO(repo))
        context.startActivity(intent)
    }
}