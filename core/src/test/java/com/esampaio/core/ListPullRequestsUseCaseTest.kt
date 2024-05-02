package com.esampaio.core

import com.esampaio.core.models.Repo
import com.esampaio.core.services.gitService.GitApiService
import com.esampaio.core.services.gitService.models.Languages
import com.esampaio.core.services.gitService.models.SearchQuery
import com.esampaio.core.services.gitService.models.SortType
import com.esampaio.core.usecases.repositories.list.ShowRepositoriesUseCase
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observers.TestObserver
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.`when`
import java.util.concurrent.TimeUnit


class RxImmediateSchedulerRule : TestRule {

    override fun apply(base: Statement, d: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
                RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
                RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
                RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

                try {
                    base.evaluate()
                } finally {
                    RxJavaPlugins.reset()
                    RxAndroidPlugins.reset()
                }
            }
        }
    }
}
class ListRepositoriesUseCaseTest {
    @Rule
    @JvmField var testSchedulerRule = RxImmediateSchedulerRule()

    lateinit var listShowRepositoriesUseCase: ShowRepositoriesUseCase
    var mockGitApiService = mock(GitApiService::class.java)

    var mockException = mock(Exception::class.java)

    @Before
    fun setupTest(){
        listShowRepositoriesUseCase = ShowRepositoriesUseCase(mockGitApiService)

    }

    val list_1 = listOf(
        Repo(1,"repo 1", "teste","eu","",1000,1000, emptyList()),
        Repo(2,"repo 2", "teste","eu","",1000,1000, emptyList()),
        Repo(3,"repo 3", "teste","eu","",1000,1000, emptyList()),
        Repo(4,"repo 4", "teste","eu","",1000,1000, emptyList()),
        Repo(5,"repo 5", "teste","eu","",1000,1000, emptyList()),
    )

    val list_2 = listOf(
        Repo(6,"repo 6", "teste","eu","",1000,1000, emptyList()),
        Repo(7,"repo 7", "teste","eu","",1000,1000, emptyList()),
        Repo(8,"repo 8", "teste","eu","",1000,1000, emptyList()),
        Repo(9,"repo 9", "teste","eu","",1000,1000, emptyList()),
        Repo(10,"repo 10", "teste","eu","",1000,1000, emptyList()),
    )


    @Test
    fun testShouldSendRepositoriesListToInteractorWhenServiceRespondsSuccessfully(){
        val testObserver = TestObserver<List<Repo>>()

        val queryParams = SearchQuery(Languages.Java, SortType.stars);
        Mockito.`when`(mockGitApiService.listAllRepositories(0,queryParams)).thenReturn(Observable.just(list_1))

        listShowRepositoriesUseCase.resultSubject.subscribe(testObserver)
        listShowRepositoriesUseCase.start(null)

        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        val listResult = testObserver.values()[0]
        assertThat(listResult, `is`(list_1))
    }

    @Test
    fun testShouldSendErrorToInteractorWhenServiceRespondsWithError(){
        val testObserver = TestObserver<List<Repo>>()

        val queryParams = SearchQuery(Languages.Java, SortType.stars);
        Mockito.`when`(mockGitApiService.listAllRepositories(0,queryParams)).thenReturn(Observable.error(mockException))
        listShowRepositoriesUseCase.resultSubject.subscribe(testObserver)
        listShowRepositoriesUseCase.start(null)

        testObserver.assertError(mockException)

    }
//
    @Test
    fun testShouldAddMoreItemsToListWhenCallOnNewPage(){
    val testObserver = TestObserver<List<Repo>>()

        val queryParams = SearchQuery(Languages.Java, SortType.stars);
        Mockito.`when`(mockGitApiService.listAllRepositories(0,queryParams)).thenReturn(Observable.just(list_1))
        Mockito.`when`(mockGitApiService.listAllRepositories(1,queryParams)).thenReturn(Observable.just(list_2))

    listShowRepositoriesUseCase.resultSubject.subscribe(testObserver)
    listShowRepositoriesUseCase.start(null)

    listShowRepositoriesUseCase.loadMore()

    testObserver.assertValueCount(2)
    val listResult = testObserver.values()[0]
    val listResult2 = testObserver.values()[1]

    assertThat(listResult, `is`(list_1))
    assertThat(listResult2, `is`(list_2))

    }
}
