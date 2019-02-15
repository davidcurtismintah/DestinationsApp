package com.allow.destinationsapp.domain.commands

import androidx.lifecycle.LiveData
import com.allow.destinationsapp.domain.datasource.DestinationsProvider
import com.allow.destinationsapp.domain.datasource.Outcome
import com.allow.destinationsapp.domain.model.Airport

class GetAirportsCommand(private val accessToken: String, private val destinationsProvider: DestinationsProvider = DestinationsProvider()) : Command<LiveData<Outcome<List<Airport>>>> {

    override fun execute(): LiveData<Outcome<List<Airport>>> {
        return destinationsProvider.getAirports(accessToken)
    }

}

