package com.allow.destinationsapp.data.server

import androidx.lifecycle.LiveData
import com.allow.destinationsapp.data.server.service.ServiceVolley
import com.allow.destinationsapp.domain.datasource.Outcome
import com.allow.destinationsapp.domain.model.Airport

class AirportsListRequest(accessToken: String) {

    companion object {
        private const val AIRPORTS_PATH = "/references/airports?limit=50&offset=0&lang=EN"
    }

    private val headers = hashMapOf(
        "Authorization" to "Bearer $accessToken",
        "Accept" to "application/json"
    )

    fun execute(): LiveData<Outcome<List<Airport>>> {
        return ServiceVolley.getAirports(AIRPORTS_PATH, headers)
    }

}

