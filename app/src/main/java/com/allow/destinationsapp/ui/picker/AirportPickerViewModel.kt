package com.allow.destinationsapp.ui.picker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.allow.destinationsapp.domain.commands.GetAirportsCommand
import com.allow.destinationsapp.domain.datasource.Outcome
import com.allow.destinationsapp.domain.model.Airport

class AirportPickerViewModel : ViewModel() {

    val accessTokenLiveData: MutableLiveData<String> = MutableLiveData()

    val airportsLiveData: LiveData<Outcome<List<Airport>>> by lazy {
        GetAirportsCommand(accessTokenLiveData.value!!).execute()
    }

}