package com.allow.destinationsapp.data.server.service

import android.content.Context
import com.allow.destinationsapp.data.server.AccessTokenDto
import org.joda.time.DateTime
import timber.log.Timber

object PrefManager {

    private const val PREF_NAME = "access_token_info"

    fun cacheAccessToken(context: Context, accessToken: AccessTokenDto) {
        with(accessToken) {
            if (expiresIn > 0 && accessToken.token.isNotEmpty()) {
                Timber.i("This is the access Token: $token. It will expire in $expiresIn secs")

                //Calculate date of expiration
                val dt = DateTime()
                val plusDuration = dt.plus(expiresIn * 1000L)
                val expireDate = plusDuration.millis

                //Store both expires in and access token in shared preferences
                val preferences = context.getSharedPreferences(PREF_NAME, 0)
                val editor = preferences.edit()
                editor.putString("access_token", token)
                editor.putString("token_type", type)
                editor.putLong("expires_in", expireDate)
                editor.apply()
            }
        }
    }

    fun getCachedAccessToken(context: Context): AccessTokenDto {
        val preferences = context.getSharedPreferences(PREF_NAME, 0)

        return AccessTokenDto(
            token = preferences.getString("access_token", "") ?: "",
            type = preferences.getString("token_type", "") ?: "",
            expiresIn = preferences.getLong("expires_in", -1)
        )
    }

}

val AccessTokenDto.isValid: Boolean
    get() = DateTime(expiresIn).isAfterNow