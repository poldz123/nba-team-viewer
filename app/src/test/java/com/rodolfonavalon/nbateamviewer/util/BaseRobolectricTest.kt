package com.rodolfonavalon.nbateamviewer.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import org.robolectric.Shadows
import org.robolectric.Shadows.shadowOf

open class BaseRobolectricTest : BaseMockServerTest() {

    inline fun <reified T : Activity> launchActivity(
        intent: Intent,
        vararg permissions: String,
        crossinline callback: (T) -> Unit
    ): ActivityScenario<T> {
        return ActivityScenario.launch<T>(intent).use { scenario ->
            scenario.onActivity { activity ->
                val application = Shadows.shadowOf(activity.application)
                permissions.forEach { permission ->
                    application.grantPermissions(permission)
                }
                callback(activity)
            }
        }
    }

    inline fun <reified T : Activity> launchActivity(
        vararg permissions: String,
        crossinline callback: (T) -> Unit
    ): ActivityScenario<T> {
        return launch<T>().use { scenario ->
            scenario.onActivity { activity ->
                val application = Shadows.shadowOf(activity.application)
                permissions.forEach { permission ->
                    application.grantPermissions(permission)
                }
                callback(activity)
            }
        }
    }

    inline fun <reified T : Activity> launch(): ActivityScenario<T> {
        return ActivityScenario.launch(T::class.java)
    }

    fun getCurrentActivity(activity: AppCompatActivity): Class<*> {
        val startedIntent = shadowOf(activity).nextStartedActivity ?: error("No activity found during transition.")
        return Class.forName(startedIntent.component!!.className)
    }
}
