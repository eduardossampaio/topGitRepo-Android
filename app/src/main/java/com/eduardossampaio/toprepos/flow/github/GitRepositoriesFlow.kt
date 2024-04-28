package com.eduardossampaio.toprepos.flow.github

import android.content.Context
import com.eduardossampaio.toprepos.flow.Flow
import com.esampaio.core.models.Repo

interface GitRepositoriesFlow: Flow{

    public fun detailRepo(context: Context, repo: Repo)
}