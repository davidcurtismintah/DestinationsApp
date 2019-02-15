package com.allow.destinationsapp.data.server

import com.google.gson.annotations.SerializedName

data class AccessTokenDto (
    @SerializedName("access_token") val token: String,
    @SerializedName("token_type") val type: String,
    @SerializedName("expires_in") val expiresIn: Long
)

data class AirportsListDto(
    @SerializedName("AirportResource")
    val airportResource: AirportResource
)

data class AirportResource(
    @SerializedName("Airports")
    val airports: Airports,
    @SerializedName("Meta")
    val meta: Meta
)

data class Airports(
    @SerializedName("Airport")
    val airport: List<Airport>
)

data class Airport(
    @SerializedName("AirportCode")
    val airportCode: String,
    @SerializedName("CityCode")
    val cityCode: String,
    @SerializedName("CountryCode")
    val countryCode: String,
    @SerializedName("LocationType")
    val locationType: String,
    @SerializedName("Names")
    val names: Names,
    @SerializedName("Position")
    val position: Position,
    @SerializedName("TimeZoneId")
    val timeZoneId: String,
    @SerializedName("UtcOffset")
    val utcOffset: Double
)

data class Coordinate(
    @SerializedName("Latitude")
    val latitude: Double,
    @SerializedName("Longitude")
    val longitude: Double
)

data class Link(
    @SerializedName("@Href")
    val href: String,
    @SerializedName("@Rel")
    val rel: String
)

data class Meta(
    @SerializedName("@Version")
    val version: String,
    @SerializedName("Link")
    val link: List<Link>,
    @SerializedName("TotalCount")
    val totalCount: Int
)

data class Name(
    @SerializedName("$")
    val x: String,
    @SerializedName("@LanguageCode")
    val languageCode: String
)

data class Names(
    @SerializedName("Name")
    val name: Name
)

data class Position(
    @SerializedName("Coordinate")
    val coordinate: Coordinate
)