package com.esampaio.core

import com.esampaio.core.models.PullRequest
import com.esampaio.core.models.Repo
import com.esampaio.core.services.gitService.GitApiService
import com.esampaio.core.services.gitService.models.Languages
import com.esampaio.core.services.gitService.models.SearchQuery
import com.esampaio.core.services.gitService.models.SortType
import com.esampaio.core.usecases.repositories.list.ShowRepositoriesInteractor
import com.esampaio.core.usecases.repositories.list.ShowRepositoriesUseCase
import com.esampaio.core.usecases.repositories.pr.ListPRUseCase
import com.esampaio.core.usecases.repositories.pr.ListPRUseCaseImpl
import com.esampaio.core.usecases.repositories.pr.ListPRUseCaseInteractor
import io.reactivex.rxjava3.core.Observable

import io.reactivex.rxjava3.subjects.PublishSubject
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

import org.mockito.Mockito
import org.mockito.Mockito.mock
import java.util.Date

class ListPullRequestsUseCaseTest {
    @Rule
    @JvmField var testSchedulerRule = RxImmediateSchedulerRule()

    class MockListPRUseCaseInteractor: ListPRUseCaseInteractor {
        var prList: List<PullRequest>? = null
        var error: Throwable? = null
        override fun showPullRequestList(prList: List<PullRequest>) {
            this.prList = prList
        }

        override fun notifyError(error: Throwable) {
            this.error = error
        }
    }

    private lateinit var listPullRequestsUseCase: ListPRUseCase

    private var mockGitApiService: GitApiService = mock(GitApiService::class.java)
    private var mockInteractor = MockListPRUseCaseInteractor()

    private var mockException: Exception = mock(Exception::class.java)

    @Before
    fun setupTest(){
        listPullRequestsUseCase = ListPRUseCaseImpl(mockGitApiService)
        listPullRequestsUseCase.interactor = mockInteractor
    }

    private val repo = Repo(1,"repo 1", "teste","eu","",1000,1000, emptyList())

    private val pullRequestsList = listOf(
        PullRequest(123, "repo 1", "Adicionando features legais", "Eduardo","",Date(),"Um monte de coisa legal"),
        PullRequest(456, "repo 1", "Adicionando features legais", "Eduardo","",Date(),"Um monte de coisa legal"),
        PullRequest(789, "repo 1", "Adicionando features legais", "Eduardo","",Date(),"Um monte de coisa legal")
    )

    @Test
    fun testShouldSendPullRequestsListToInteractorWhenServiceRespondsSuccessfully(){
        Mockito.`when`(mockGitApiService.listPullRequests(repo)).thenReturn(Observable.just(pullRequestsList))
        listPullRequestsUseCase.start(repo)
        assertNotNull(mockInteractor.prList)
        assertEquals(mockInteractor.prList?.size, pullRequestsList.size)

    }

    @Test
    fun testShouldSendErrorToInteractorWhenServiceRespondsWithError(){
        Mockito.`when`(mockGitApiService.listPullRequests(repo)).thenReturn(Observable.error(mockException))
        listPullRequestsUseCase.start(repo)
        assertNull(mockInteractor.prList)
        assertNotNull(mockInteractor.error)
    }

}
