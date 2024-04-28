package com.eduardossampaio.toprepos.flow.github

import android.content.Context
import android.content.Intent
import com.eduardossampaio.toprepos.flow.Flow
import com.eduardossampaio.toprepos.features.list_repos.view.ListRepositoriesActivity

class GitRepositoriesFlow: Flow{
    override fun start(context: Context) {

        val intent = Intent(context, ListRepositoriesActivity::class.java)
        context.startActivity(intent);
    }
}