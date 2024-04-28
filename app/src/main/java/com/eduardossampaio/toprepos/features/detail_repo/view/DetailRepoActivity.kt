package com.eduardossampaio.toprepos.features.detail_repo.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import com.eduardossampaio.toprepos.databinding.ActivityDetailRepoBinding
import com.eduardossampaio.toprepos.flow.github.dto.RepoDTO


class DetailRepoActivity : AppCompatActivity() {
    lateinit var views: ActivityDetailRepoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        views = ActivityDetailRepoBinding.inflate(layoutInflater)
        setContentView(views.root)
        val repoDTO:RepoDTO? = IntentCompat.getParcelableExtra(intent, "example", RepoDTO::class.java)
        repoDTO.let {
            views.title.text = it?.name ?: "Nada"
        }

    }
}