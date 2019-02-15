package com.allow.destinationsapp.data.server

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.allow.destinationsapp.data.server.service.ServiceVolley
import com.allow.destinationsapp.domain.datasource.Outcome
import com.allow.destinationsapp.domain.model.AccessToken
import com.google.gson.Gson
import timber.log.Timber
import java.net.URL

class AccessTokenRequest {

    companion object {
        private const val AUTH_PATH = "/oauth/token"
        private val headers = hashMapOf(
            "Content-Type" to "application/x-www-form-urlencoded"
        )
        private val params = hashMapOf(
            "client_id" to "4rxfjxe7chz9jdjbqssjynj2",
            "client_secret" to "7pwv3YaW52",
            "grant_type" to "client_credentials"
        )
    }

    fun execute(): LiveData<Outcome<AccessToken>> {
        return ServiceVolley.requestAccessToken(AUTH_PATH, headers, params)
    }

}

