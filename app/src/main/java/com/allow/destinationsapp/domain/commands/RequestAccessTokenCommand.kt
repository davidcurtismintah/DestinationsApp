package com.allow.destinationsapp.domain.commands

import androidx.lifecycle.LiveData
import com.allow.destinationsapp.domain.datasource.DestinationsProvider
import com.allow.destinationsapp.domain.datasource.Outcome
import com.allow.destinationsapp.domain.model.AccessToken

class RequestAccessTokenCommand(private val destinationsProvider: DestinationsProvider = DestinationsProvider()) : Command<LiveData<Outcome<AccessToken>>> {

    override fun execute(): LiveData<Outcome<AccessToken>> {
        return destinationsProvider.requestAccessToken()
    }

}

