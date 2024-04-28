package com.eduardossampaio.toprepos

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.runner.AndroidJUnitRunner
import com.eduardossampaio.toprepos.features.list_repos.interactor.ListRepositoriesInteractor
import com.eduardossampaio.toprepos.features.list_repos.view.ListRepositoriesActivity
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import org.mockito.Mockito.mock

/**
 * Desculpa, não consegui fazer melhor por não estar conseguindo mockar
 * as dependencias do koin dentro dessa classe de teste. Tentei por horas e não sei o que eu errei : (
 */
@RunWith(AndroidJUnit4::class)
class KoinModuleRule(private val modules: List<Module> = emptyList()) : TestWatcher() {

        override fun starting(description: Description) {
                super.starting(description)
                // Since we're not override the application class, we're starting koin on startup of app so need to stop that instance
                stopKoin()
                startKoin {
                        modules(ThisApplication.ModulesProvider.modules.toMutableList().apply { addAll(modules) })
                }
        }

        override fun finished(description: Description) {
                super.finished(description)
                stopKoin()
        }
}

class ScrollToBottomAction : ViewAction {
        override fun getDescription(): String {
                return "scroll RecyclerView to bottom"
        }

        override fun getConstraints(): Matcher<View> {
                return allOf<View>(isAssignableFrom(RecyclerView::class.java), isDisplayed())
        }

        override fun perform(uiController: UiController?, view: View?) {
                val recyclerView = view as RecyclerView
                val itemCount = recyclerView.adapter?.itemCount
                val position = itemCount?.minus(1) ?: 0
                recyclerView.scrollToPosition(position)
                uiController?.loopMainThreadUntilIdle()
        }
}

class ListRepositoriesActivityTest : AndroidJUnitRunner() {

        var interactorMock: ListRepositoriesInteractor = mock(ListRepositoriesInteractor::class.java)


        private val instrumentedTestModule = module() {
                factory<ListRepositoriesInteractor> { interactorMock }
        }


        @get : Rule
        var mActivityRule = ActivityScenarioRule(ListRepositoriesActivity::class.java)

        @Before
        fun beforeEach(){
                stopKoin()
                startKoin {
                        modules(ThisApplication.ModulesProvider.modules.toMutableList().apply { add(instrumentedTestModule) })
                }
        }

        @Test
        fun waitForListToShow() {
                Thread.sleep(3000);
                onView(withId(R.id.repoList)).check(matches(isDisplayed()))
        }

        @Test
        fun scrollListToEndAndAddItems() {
                Thread.sleep(3000);

                onView(withId(R.id.repoList))
                        .perform(ScrollToBottomAction());

                Thread.sleep(3000);


        }

}