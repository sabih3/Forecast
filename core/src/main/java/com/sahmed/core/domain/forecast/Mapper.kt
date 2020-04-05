package com.sahmed.core.domain.forecast

interface Mapper<R, D> {
    fun mapFrom(type: R): D
}