package com.esampaio.core

import com.esampaio.core.models.PullRequest
import com.esampaio.core.models.Repo
import com.esampaio.core.services.gitService.GitApiService
import com.esampaio.core.usecases.repositories.pr.ListPRUseCase
import com.esampaio.core.usecases.repositories.pr.ListPRUseCaseImpl
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observers.TestObserver
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import java.util.Date

class ListPullRequestsUseCaseTest {
    @Rule
    @JvmField var testSchedulerRule = RxImmediateSchedulerRule()

    private lateinit var listPullRequestsUseCase: ListPRUseCase

    private var mockGitApiService: GitApiService = mock(GitApiService::class.java)

    private var mockException: Exception = mock(Exception::class.java)

    @Before
    fun setupTest(){
        listPullRequestsUseCase = ListPRUseCaseImpl(mockGitApiService)
    }

    private val repo = Repo(1,"repo 1", "teste","eu","",1000,1000, emptyList())

    private val pullRequestsList = listOf(
        PullRequest(123, "repo 1", "Adicionando features legais", "Eduardo","",Date(),"Um monte de coisa legal"),
        PullRequest(456, "repo 1", "Adicionando features legais", "Eduardo","",Date(),"Um monte de coisa legal"),
        PullRequest(789, "repo 1", "Adicionando features legais", "Eduardo","",Date(),"Um monte de coisa legal")
    )

    @Test
    fun testShouldSendPullRequestsListToInteractorWhenServiceRespondsSuccessfully(){
        val testObserver = TestObserver<List<PullRequest>>()
        Mockito.`when`(mockGitApiService.listPullRequests(repo)).thenReturn(Observable.just(pullRequestsList))
        listPullRequestsUseCase.start(repo).subscribe(testObserver)

        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        val listResult = testObserver.values()[0]
        MatcherAssert.assertThat(listResult, CoreMatchers.`is`(listResult))

    }

    @Test
    fun testShouldSendErrorToInteractorWhenServiceRespondsWithError(){
        val testObserver = TestObserver<List<PullRequest>>()
        Mockito.`when`(mockGitApiService.listPullRequests(repo)).thenReturn(Observable.error(mockException))
        listPullRequestsUseCase.start(repo).subscribe(testObserver)
        testObserver.assertError(mockException)

    }

}
