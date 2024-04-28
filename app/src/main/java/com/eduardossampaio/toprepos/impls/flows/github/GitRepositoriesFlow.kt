package com.eduardossampaio.toprepos.impls.flows.github

import android.content.Context
import android.content.Intent
import android.util.Log
import com.eduardossampaio.toprepos.flow.Flow
import com.eduardossampaio.toprepos.views.acitivties.list_repositories.ListRepositoriesActivity

class GitRepositoriesFlow: Flow{
    override fun start(context: Context) {
        Log.d("Flow", "começando")

        val intent = Intent(context, ListRepositoriesActivity::class.java)
        context.startActivity(intent);
    }
}