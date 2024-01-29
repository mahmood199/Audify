package com.example.data.mapper

// UI would show local data base entities and not remote ones
interface Mapper<F, T> {

    fun map(from: F): T

    fun mapBack(from: T): F

}
