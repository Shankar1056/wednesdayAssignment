package com.wednesdayassignment

import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import com.mpokketassignment.Utilz.NetworkStateMonitor

class AppController : Application(), ActivityLifecycleCallbacks {
    var networkStateMonitor: NetworkStateMonitor? = null
    private var activity: Activity? = null
    override fun onCreate() {
        super.onCreate()
        instance = this
        networkStateMonitor = NetworkStateMonitor(applicationContext)
    }

    override fun onActivityCreated(activity: Activity, bundle: Bundle) {}
    override fun onActivityStarted(activity: Activity) {}
    override fun onActivityResumed(activity: Activity) {
        this.activity = activity
    }

    override fun onActivityPaused(activity: Activity) {
        this.activity = null
    }

    override fun onActivityStopped(activity: Activity) {}
    override fun onActivitySaveInstanceState(
        activity: Activity,
        bundle: Bundle
    ) {
    }

    override fun onActivityDestroyed(activity: Activity) {}

    companion object {
        var instance: AppController? = null
            private set
    }
}