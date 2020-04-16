package ru.startandroid.domain.mapping

interface Mapper<I, O> {
    fun map(input: I): O
}