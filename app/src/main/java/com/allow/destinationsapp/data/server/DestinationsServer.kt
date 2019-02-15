package com.allow.destinationsapp.data.server

import androidx.lifecycle.LiveData
import com.allow.destinationsapp.domain.datasource.DestinationsDataSource
import com.allow.destinationsapp.domain.datasource.Outcome
import com.allow.destinationsapp.domain.datasource.Provider
import com.allow.destinationsapp.domain.model.AccessToken
import com.allow.destinationsapp.domain.model.Airport

class DestinationsServer : DestinationsDataSource {

    companion object : Provider<DestinationsDataSource>() {
        override fun creator(): DestinationsDataSource = DestinationsServer()
    }

    override fun requestAccessToken(): LiveData<Outcome<AccessToken>> {
        return AccessTokenRequest().execute()
    }

    override fun getAirports(accessToken: String): LiveData<Outcome<List<Airport>>> {
        return AirportsListRequest(accessToken).execute()
    }
}
