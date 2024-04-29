package com.esampaio.core

import com.esampaio.core.models.Repo
import com.esampaio.core.services.gitService.GitApiService
import com.esampaio.core.services.gitService.models.Languages
import com.esampaio.core.services.gitService.models.SearchQuery
import com.esampaio.core.services.gitService.models.SortType
import com.esampaio.core.usecases.repositories.list.ShowRepositoriesInteractor
import com.esampaio.core.usecases.repositories.list.ShowRepositoriesUseCase
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.mockito.Mockito
import org.mockito.Mockito.mock



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


    class MockShowRepositoriesInteractor: ShowRepositoriesInteractor{
        var error:Throwable? = null
        var repositories: List<Repo>? = null
        val onPageChangedSubject =  PublishSubject.create<Int>()
        override fun showRepositories(repositories: List<Repo>) {
            this.repositories = repositories
        }

        override val onPageChanged: Observable<Int> = onPageChangedSubject


        override fun notifyError(error: Throwable) {
            this.error = error
        }

    }

    lateinit var listShowRepositoriesUseCase: ShowRepositoriesUseCase

    var mockGitApiService = mock(GitApiService::class.java)
    var mockInteractor = MockShowRepositoriesInteractor()

    var mockException = mock(Exception::class.java)

    @Before
    fun setupTest(){
        listShowRepositoriesUseCase = ShowRepositoriesUseCase(mockGitApiService)
        listShowRepositoriesUseCase.showRepositoriesInteractor = mockInteractor
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
        val queryParams = SearchQuery(Languages.Java, SortType.stars);
        Mockito.`when`(mockGitApiService.listAllRepositories(0,queryParams)).thenReturn(Observable.just(list_1))
        listShowRepositoriesUseCase.start(null)
        assertNotNull(mockInteractor.repositories)
        assertEquals(mockInteractor.repositories?.size, list_1.size)

    }

    @Test
    fun testShouldSendErrorToInteractorWhenServiceRespondsWithError(){
        val queryParams = SearchQuery(Languages.Java, SortType.stars);
        Mockito.`when`(mockGitApiService.listAllRepositories(0,queryParams)).thenReturn(Observable.error(mockException))
        listShowRepositoriesUseCase.start(null)
        assertNull(mockInteractor.repositories)
        assertNotNull(mockInteractor.error)
    }

    @Test
    fun testShouldAddMoreItemsToListWhenCallOnNewPage(){
        val queryParams = SearchQuery(Languages.Java, SortType.stars);
        Mockito.`when`(mockGitApiService.listAllRepositories(0,queryParams)).thenReturn(Observable.just(list_1))
        Mockito.`when`(mockGitApiService.listAllRepositories(1,queryParams)).thenReturn(Observable.just(list_2))
        listShowRepositoriesUseCase.start(null)
        assertEquals(mockInteractor.repositories, list_1)

        mockInteractor.onPageChangedSubject.onNext(1)
        assertEquals(mockInteractor.repositories, list_2)



    }
}
