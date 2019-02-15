package com.allow.destinationsapp.data.server

import com.allow.destinationsapp.domain.model.AccessToken
import com.allow.destinationsapp.domain.model.Airport

class ServerDataMapper {

    fun convertToDomain(accessTokenDto: AccessTokenDto) = AccessToken(
        token = accessTokenDto.token,
        type = accessTokenDto.type,
        expiresIn = accessTokenDto.expiresIn
    )

    fun convertToDomain(airportsDto: AirportsListDto): List<Airport> {
        return airportsDto.airportResource.airports.airport.map { Airport(
            name = it.names.name.x,
            latitude = it.position.coordinate.latitude,
            longitude = it.position.coordinate.longitude
        ) }
    }

}