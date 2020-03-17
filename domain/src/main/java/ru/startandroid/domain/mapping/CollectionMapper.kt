package ru.startandroid.domain.mapping

interface CollectionMapper<I, O>: Mapper<Collection<I>, Collection<O>>

class CollectionMapperImpl<I, O>(
    private val mapper: Mapper<I,O>
): CollectionMapper<I,O> {
    override fun map(input: Collection<I>): Collection<O> =
        input.map { mapper.map(it) }
}