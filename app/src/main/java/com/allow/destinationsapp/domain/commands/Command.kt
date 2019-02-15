package com.allow.destinationsapp.domain.commands

interface Command<out T> {
    fun execute(): T
}