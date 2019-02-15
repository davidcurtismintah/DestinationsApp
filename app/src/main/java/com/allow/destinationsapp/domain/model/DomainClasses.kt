package com.allow.destinationsapp.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class AccessToken(
    val token: String,
    val type: String,
    val expiresIn: Long
)

@Parcelize
data class Airport(
    val name: String,
    val latitude: Double,
    val longitude: Double
) : Parcelable