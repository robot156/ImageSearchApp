package com.example.imagesearchapp.data.source

import com.example.imagesearchapp.domain.usecase.Entity

abstract class EntityMapper<E : Entity, M : Model> {

    abstract fun fromEntityToModel(entity: E): M

    abstract fun fromModelToEntity(model: M): E
}