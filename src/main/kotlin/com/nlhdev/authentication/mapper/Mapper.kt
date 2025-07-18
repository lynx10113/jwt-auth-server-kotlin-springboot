package com.nlhdev.authentication.mapper

interface Mapper<D,E> {
    fun mapToDomain(entity: E): D
//    fun mapToEntity(domain: D): E
}