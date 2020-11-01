package com.rodolfonavalon.nbateamviewer.util

import com.rodolfonavalon.nbateamviewer.di.appcomponent.module.AppModule
import com.rodolfonavalon.nbateamviewer.util.di.appcomponent.DaggerTestAppComponent
import org.junit.*
import javax.inject.Inject

/**
 * Base class for testing with the mock server. This is mainly used for
 * networking test cases to have a trampoline scheduler that will block
 * threading.
 *
 * This also handles assertion if ever some of the request has not been
 * consumed by the test case, also triggers to start and stop the mock
 * server before and after the test suite.
 *
 * IMPORTANT: That during [setup] the URL endpoint from the mock server
 * should be used by the API class as the base URL.
 */
open class BaseMockServerTest : BaseTest() {

    companion object {
        lateinit var server: NbaMockWebServer

        @BeforeClass
        @JvmStatic
        fun setupClass() {
            server = NbaMockWebServer()
            server.start()
        }

        @AfterClass
        @JvmStatic
        fun teardownClass() {
            server.stop()
        }
    }

    @Before
    override fun setup() {
        super.setup()
        // Reset the server per test cases, this
        // to have a clean slate on it
        server.reset()
    }

    @After
    override fun teardown() {
        super.teardown()
        // Check the server that all of the responses are consumed
        server.check()
    }
}
