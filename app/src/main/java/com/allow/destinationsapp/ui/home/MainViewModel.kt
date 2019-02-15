package com.allow.destinationsapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.allow.destinationsapp.domain.commands.RequestAccessTokenCommand
import com.allow.destinationsapp.domain.datasource.Outcome
import com.allow.destinationsapp.domain.model.AccessToken
import com.allow.destinationsapp.domain.model.Airport

class MainViewModel : ViewModel() {

    val accessTokenLiveData: LiveData<Outcome<AccessToken>> by lazy {
        RequestAccessTokenCommand().execute()
    }

    val originLiveData: MutableLiveData<Airport> = MutableLiveData()
    val destinationLiveData: MutableLiveData<Airport> = MutableLiveData()

}