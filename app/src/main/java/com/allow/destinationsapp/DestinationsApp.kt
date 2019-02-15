package com.allow.destinationsapp

import android.app.Application
import android.text.TextUtils
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import timber.log.Timber

class DestinationsApp : Application() {

    companion object {
        private val DEFAULT_REQUEST_TAG = DestinationsApp::class.java.simpleName
        lateinit var instance: DestinationsApp
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        Timber.plant(Timber.DebugTree())
    }

    private val requestQueue: RequestQueue by lazy { Volley.newRequestQueue(applicationContext) }

    fun <T> addToRequestQueue(request: Request<T>, tag: String) {
        request.tag = if (TextUtils.isEmpty(tag)) DEFAULT_REQUEST_TAG else tag
        requestQueue.add(request)
    }

    fun <T> addToRequestQueue(request: Request<T>) {
        request.tag = DEFAULT_REQUEST_TAG
        requestQueue.add(request)
    }

    fun cancelPendingRequests(tag: Any) {
        requestQueue.cancelAll(tag)
    }

}
