package com.allow.destinationsapp.ui.home

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.allow.destinationsapp.R
import com.allow.destinationsapp.domain.datasource.Outcome
import com.allow.destinationsapp.domain.model.AccessToken
import com.allow.destinationsapp.domain.model.Airport
import com.allow.destinationsapp.ui.common.snack
import com.allow.destinationsapp.ui.map.MapsActivity
import com.allow.destinationsapp.ui.picker.AirportPickerActivity
import com.allow.destinationsapp.ui.picker.AirportPickerActivity.Companion.EXTRA_AIRPORT
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class MainActivity : AppCompatActivity() {

    companion object {
        const val RC_ORIGIN_AIRPORT = 100
        const val RC_DESTINATION_AIRPORT = 200
    }

    /*init {
        DestinationsServer.testingInstance = object : DestinationsDataSource {
            override fun requestAccessToken(): LiveData<Outcome<AccessToken>> {
                return MutableLiveData(
                    Outcome.success(
                        AccessToken(
                            token = "ybz9t4grfv59hshwsg9j6js4",
                            type = "bearer",
                            expiresIn = 21600
                        )
                    )
                )
            }

            override fun getAirports(accessToken: String): LiveData<Outcome<List<Airport>>> {
                return MutableLiveData(
                    Outcome.success(
                        listOf(
                            Airport(
                                name = "Dammam",
                                latitude = 26.47055556,
                                longitude = 49.79833333
                            ),
                            Airport(
                                name = "Dnipropetrovsk-42",
                                latitude = 48.35722222,
                                longitude = 35.10055556
                            ),
                            Airport(
                                name = "Doha",
                                latitude = 25.27444444,
                                longitude = 51.60833333
                            )
                        )
                    )
                )
            }

        }
    }*/

    private lateinit var mMainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pick_origin_button.setOnClickListener {
            (mMainViewModel.accessTokenLiveData.value as? Outcome.Success)?.data?.let {
                startActivityForResult(AirportPickerActivity.startIntent(this, it.token), RC_ORIGIN_AIRPORT)
            }
        }

        pick_destination_button.setOnClickListener {
            (mMainViewModel.accessTokenLiveData.value as? Outcome.Success)?.data?.let {
                startActivityForResult(AirportPickerActivity.startIntent(this, it.token), RC_DESTINATION_AIRPORT)
            }
        }

        show_map_button.setOnClickListener {
            val origin = mMainViewModel.originLiveData.value
            val destination = mMainViewModel.destinationLiveData.value
            if (origin !is Airport || destination !is Airport) {
                snack(
                    pick_origin_destination_layout,
                    getString(
                        when {
                            origin !is Airport && destination !is Airport -> R.string.airports_not_chosen_error_message
                            origin !is Airport -> R.string.origin_airport_not_chosen_error_message
                            destination !is Airport -> R.string.destination_airport_not_chosen_error_message
                            else -> -1
                        }
                    )
                )
                return@setOnClickListener
            }
            startActivity(MapsActivity.startIntent(this, origin, destination))
        }

        mMainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mMainViewModel.accessTokenLiveData.observe(this, Observer {
            it?.let { accessTokenOutcome ->
                requestAccessTokenOutcome(accessTokenOutcome)
            }
        })
        mMainViewModel.originLiveData.observe(this, Observer {
            it?.let { origin ->
                showOriginAirportView(origin)
            }
        })
        mMainViewModel.destinationLiveData.observe(this, Observer {
            it?.let { destination ->
                showDestinationAirportView(destination)
            }
        })
    }

    private fun requestAccessTokenOutcome(outcome: Outcome<AccessToken>) {

        when (outcome) {

            is Outcome.Progress -> {
                Timber.d("Started Obtaining AccessToken")
            }

            is Outcome.Success -> {
                Timber.d("Obtained AccessToken: ${outcome.data}")
                showAuthenticatedView()
            }

            is Outcome.Failure -> {
                Timber.e(outcome.error, "Error Obtaining AccessToken")
                snack(
                    pick_origin_destination_layout,
                    getString(R.string.default_error_message, outcome.error.message),
                    Snackbar.LENGTH_INDEFINITE
                )
            }
        }
    }

    private fun showAuthenticatedView() {
        pick_origin_card_view.visibility = View.VISIBLE
        pick_destination_card_view.visibility = View.VISIBLE
        show_map_button.visibility = View.VISIBLE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val airport = data?.extras?.getParcelable<Airport>(EXTRA_AIRPORT)
            airport?.let {
                when (requestCode) {
                    RC_ORIGIN_AIRPORT -> {
                        mMainViewModel.originLiveData.value = it
                        showOriginAirportView(it)
                    }
                    RC_DESTINATION_AIRPORT -> {
                        mMainViewModel.destinationLiveData.value = it
                        showDestinationAirportView(it)
                    }
                    else -> {
                        // No op
                    }
                }
            }
        }
    }

    private fun showDestinationAirportView(it: Airport) {
        pick_destination_empty_address_view.visibility = View.INVISIBLE
        pick_destination_airport.visibility = View.VISIBLE
        pick_destination_airport.text = it.name
    }

    private fun showOriginAirportView(it: Airport) {
        pick_origin_empty_address_view.visibility = View.INVISIBLE
        pick_origin_airport.visibility = View.VISIBLE
        pick_origin_airport.text = it.name
    }
}
