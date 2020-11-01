package com.rodolfonavalon.nbateamviewer.unit

import com.rodolfonavalon.nbateamviewer.data.NbaRepository
import com.rodolfonavalon.nbateamviewer.data.local.NbaLocalDataSource
import com.rodolfonavalon.nbateamviewer.data.remote.NbaApi
import com.rodolfonavalon.nbateamviewer.data.remote.NbaRemoteDataSource
import com.rodolfonavalon.nbateamviewer.util.BaseMockServerTest
import com.rodolfonavalon.nbateamviewer.util.di.appcomponent.DaggerTestAppComponent
import com.rodolfonavalon.nbateamviewer.util.di.appcomponent.module.TestAppModule
import org.junit.Test
import javax.inject.Inject

class NbaRepositoryTest : BaseMockServerTest() {

    @Inject
    lateinit var repository: NbaRepository

    override fun setup() {
        super.setup()
        DaggerTestAppComponent.factory().create(null, TestAppModule(server.url("/").toString())).into(this)
    }

    @Test
    fun hi() {
        assert(true)
    }
}