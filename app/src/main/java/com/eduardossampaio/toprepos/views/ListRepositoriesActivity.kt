package com.eduardossampaio.toprepos.views

import android.os.Bundle
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.eduardossampaio.toprepos.R
import com.eduardossampaio.toprepos.impls.services.github.GithubApiService
import com.esampaio.core.models.Repo
import com.esampaio.core.presenters.repositories.ShowRepositoriesPresenter
import com.esampaio.core.usecases.UseCase
import com.esampaio.core.usecases.repositories.ShowRepositoriesUseCase
import io.reactivex.rxjava3.core.Observable

class ListRepositoriesActivity : AppCompatActivity(), ShowRepositoriesPresenter {

    override val onRepositoryClicked: Observable<Repo>
        get() = TODO("Not yet implemented")
    override val onPageChanged: Observable<Int>
        get() = TODO("Not yet implemented")

    //temp
    lateinit var useCase: UseCase;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(R.string.list_repo_title)

//        loading.visibility = View.GONE;
//        enableEdgeToEdge()
        setContentView(R.layout.activity_list_repositories)


//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        //temp
        useCase = ShowRepositoriesUseCase();

        (useCase as ShowRepositoriesUseCase).showRepositoriesPresenter = this
        (useCase as ShowRepositoriesUseCase).gitApiService = GithubApiService()

        //useCase.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        useCase.finish()
    }



    override fun showRepositories(repo: List<Repo>) {

    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showError(error: Throwable) {

    }
}