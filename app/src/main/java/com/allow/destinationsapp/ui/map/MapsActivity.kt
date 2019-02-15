package com.allow.destinationsapp.ui.map

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import com.allow.destinationsapp.R
import com.allow.destinationsapp.domain.model.Airport
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object {
        private const val EXTRA_ORIGIN = "origin"
        private const val EXTRA_DESTINATION = "destination"

        fun startIntent(ctx: Context, origin: Airport, destination: Airport) =
            Intent(ctx, MapsActivity::class.java).apply {
                putExtra(MapsActivity.EXTRA_ORIGIN, origin)
                putExtra(MapsActivity.EXTRA_DESTINATION, destination)
            }
    }

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val origin = intent?.extras?.getParcelable<Airport>(EXTRA_ORIGIN)
        val destination = intent?.extras?.getParcelable<Airport>(EXTRA_DESTINATION)

        if (origin !is Airport || destination !is Airport) return

        val originLatLng = LatLng(origin.latitude, origin.longitude)
        val destinationLatLng = LatLng(destination.latitude, destination.longitude)

        // add markers
        val originMarker = mMap.addMarker(MarkerOptions().position(originLatLng).title(origin.name))
        val destinationMarker = mMap.addMarker(MarkerOptions().position(destinationLatLng).title(destination.name))

        // add circles
        mMap.addCircle(CircleOptions()
            .center(originLatLng)
            .radius(150000.0)
            .strokeWidth(3f)
            .strokeColor(Color.RED)
            .fillColor(Color.argb(70, 150, 50, 50)))

        mMap.addCircle(CircleOptions()
            .center(destinationLatLng)
            .radius(150000.0)
            .strokeWidth(3f)
            .strokeColor(Color.RED)
            .fillColor(Color.argb(70, 150, 50, 50)))

        // add poly lines
        mMap.addPolyline(
            PolylineOptions()
                .add(originLatLng)
                .add(destinationLatLng)
                .width(8f)
                .color(Color.RED)
        )

        val builder = LatLngBounds.Builder()
        builder.include(originMarker.position)
        builder.include(destinationMarker.position)
        val bounds = builder.build()

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels

        val padding = width * 20 / 100 // offset from edges of the map in pixels
        val cu = CameraUpdateFactory.newLatLngBounds(
            bounds,
            padding
        )
        mMap.animateCamera(cu)
    }
}
