package com.rodolfonavalon.nbateamviewer.util

import com.rodolfonavalon.nbateamviewer.util.rule.SynchronousTestRule
import io.reactivex.disposables.CompositeDisposable
import org.junit.After
import org.junit.Before
import org.junit.Rule

open class BaseTest {

    @get:Rule
    val synchronousTask = SynchronousTestRule()

    lateinit var compositeDisposable: CompositeDisposable

    @Before
    open fun setup() {
        compositeDisposable = CompositeDisposable()
    }

    @After
    open fun teardown() {
    }
}
