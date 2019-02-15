package com.allow.destinationsapp.domain.datasource

/**
 * Allows setting different implementations of an interface for testing purposes.
 */
abstract class Provider<T> {
    abstract fun creator(): T

    private val instance: T by lazy { creator() }
    var testingInstance: T? = null

    fun get(): T = testingInstance ?: instance
}

