package com.allow.destinationsapp.ui.picker

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.allow.destinationsapp.R
import com.allow.destinationsapp.domain.datasource.Outcome
import com.allow.destinationsapp.domain.model.Airport
import com.allow.destinationsapp.ui.common.snack
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_airport_picker.*
import timber.log.Timber

class AirportPickerActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ACCESS_TOKEN = "access_token"
        const val EXTRA_AIRPORT = "airport"

        fun startIntent(ctx: Context, accessToken: String) =
            Intent(ctx, AirportPickerActivity::class.java).apply {
                putExtra(AirportPickerActivity.EXTRA_ACCESS_TOKEN, accessToken)
            }
    }

    lateinit var mAirportPickerViewModel: AirportPickerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_airport_picker)
        recyclerView.layoutManager = LinearLayoutManager(this)

        mAirportPickerViewModel = ViewModelProviders.of(this).get(AirportPickerViewModel::class.java)
        mAirportPickerViewModel.accessTokenLiveData.value = intent?.extras?.getString(EXTRA_ACCESS_TOKEN)

        mAirportPickerViewModel = ViewModelProviders.of(this).get(AirportPickerViewModel::class.java)
        mAirportPickerViewModel.airportsLiveData.observe(this, Observer {
            it?.let { airportsOutcome ->
                getAirportsOutcome(airportsOutcome)
            }
        })
    }

    private fun getAirportsOutcome(outcome: Outcome<List<Airport>>) {

        when (outcome) {

            is Outcome.Progress -> {
                Timber.d("Started Obtaining Airports list")
            }

            is Outcome.Success -> {
                Timber.d("Obtained Airports list: ${outcome.data}")
                showListOfAirports(outcome.data)
            }

            is Outcome.Failure -> {
                Timber.e(outcome.error, "Error Obtaining Airports list")
                snack(recyclerView,
                    getString(R.string.default_error_message, outcome.error.message),
                    Snackbar.LENGTH_INDEFINITE)
            }
        }
    }

    private fun showListOfAirports(items: List<Airport>) {
        val categoryItemAdapters = items.map(this::createCategoryItemAdapter)
        recyclerView.adapter = MainListAdapter(categoryItemAdapters)
    }

    private fun createCategoryItemAdapter(airport: Airport): AirportItemAdapter =
        AirportItemAdapter(
            airport,
            clicked = { returnPickerResult(airport) }
        )

    private fun returnPickerResult(airport: Airport) {
        setResult(Activity.RESULT_OK, Intent().apply {
            putExtra(EXTRA_AIRPORT, airport)
        })
        finish()
    }
}
