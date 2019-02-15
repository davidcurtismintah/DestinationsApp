package com.allow.destinationsapp.domain.datasource

import androidx.lifecycle.LiveData
import com.allow.destinationsapp.data.server.DestinationsServer
import com.allow.destinationsapp.domain.model.AccessToken
import com.allow.destinationsapp.domain.model.Airport
import com.allow.destinationsapp.extensions.firstResult

class DestinationsProvider(private val sources: List<DestinationsDataSource> = SOURCES) {

    companion object {
        val SOURCES = listOf(DestinationsServer.get())
    }

    fun requestAccessToken(): LiveData<Outcome<AccessToken>> = requestToSources {
        it.requestAccessToken()
    }

    fun getAirports(accessToken: String): LiveData<Outcome<List<Airport>>> = requestToSources {
        it.getAirports(accessToken)
    }

    private fun <T : Any> requestToSources(f: (DestinationsDataSource) -> T?): T
            = sources.firstResult { f(it) }
}