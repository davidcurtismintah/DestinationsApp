package com.allow.destinationsapp.domain.datasource

import androidx.lifecycle.LiveData
import com.allow.destinationsapp.domain.model.AccessToken
import com.allow.destinationsapp.domain.model.Airport

interface DestinationsDataSource {

    fun requestAccessToken(): LiveData<Outcome<AccessToken>>
    fun getAirports(accessToken: String): LiveData<Outcome<List<Airport>>>
}
