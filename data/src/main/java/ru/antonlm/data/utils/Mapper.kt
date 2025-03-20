package ru.antonlm.data.utils

interface Mapper<in I, out O> {
    fun map(input: I): O
}
