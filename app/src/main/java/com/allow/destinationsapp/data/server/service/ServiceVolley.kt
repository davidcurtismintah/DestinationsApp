package com.allow.destinationsapp.data.server.service

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.allow.destinationsapp.DestinationsApp
import com.allow.destinationsapp.data.server.AccessTokenDto
import com.allow.destinationsapp.data.server.AirportsListDto
import com.allow.destinationsapp.data.server.ServerDataMapper
import com.allow.destinationsapp.domain.datasource.Outcome
import com.allow.destinationsapp.domain.model.AccessToken
import com.allow.destinationsapp.domain.model.Airport
import com.android.volley.Request
import com.android.volley.Response
import timber.log.Timber


object ServiceVolley {

    private val REQUEST_TAG: String = ServiceVolley::class.java.simpleName
    private const val BASE_PATH = "https://api.lufthansa.com/v1"

    fun requestAccessToken(
        path: String,
        headers: HashMap<String, String>,
        params: HashMap<String, String>,
        dataMapper: ServerDataMapper = ServerDataMapper()
    ): LiveData<Outcome<AccessToken>> {
        val result = MutableLiveData<Outcome<AccessToken>>()

        val cached = PrefManager.getCachedAccessToken(DestinationsApp.instance.applicationContext)
        if (cached.isValid){
            Timber.d("Obtained AccessToken from cache")
            result.value = Outcome.success(dataMapper.convertToDomain(cached))
            return result
        }

        result.value = Outcome.loading(true)
        val accessTokenReq = AuthRequest(
            method = Request.Method.POST,
            url = BASE_PATH + path,
            clazz = AccessTokenDto::class.java,
            headers = headers,
            params = params,
            listener = Response.Listener { response ->
                Timber.d("Retrieve AccessToken api call OK! Response: $response")
                PrefManager.cacheAccessToken(DestinationsApp.instance.applicationContext, response)
                result.value = Outcome.success(dataMapper.convertToDomain(PrefManager.getCachedAccessToken(DestinationsApp.instance.applicationContext)))
            },
            errorListener = Response.ErrorListener { error ->
                Timber.e("Retrieve AccessToken api call fail! Error: ${error.message}")
                result.value = Outcome.failure(error)
            }
        )
        DestinationsApp.instance.addToRequestQueue(
            accessTokenReq,
            REQUEST_TAG
        )
        return result
    }

    fun getAirports(path: String, headers: HashMap<String, String>, dataMapper: ServerDataMapper = ServerDataMapper()): LiveData<Outcome<List<Airport>>> {
        val result = MutableLiveData<Outcome<List<Airport>>>()
        result.value = Outcome.loading(true)
        val accessTokenReq = AuthRequest(
            url = BASE_PATH + path,
            clazz = AirportsListDto::class.java,
            headers = headers,
            listener = Response.Listener { response ->
                Timber.d("Get Airports api call OK! Response: $response")
                result.value = Outcome.success(dataMapper.convertToDomain(response))
            },
            errorListener = Response.ErrorListener { error ->
                Timber.e("Get Airports api call fail! Error: ${error.message}")
                result.value = Outcome.failure(error)
            }
        )
        DestinationsApp.instance.addToRequestQueue(
            accessTokenReq,
            REQUEST_TAG
        )
        return result
    }
}
