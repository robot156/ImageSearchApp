package com.example.imagesearchapp.data.source.unsplashimage.model

import com.example.imagesearchapp.data.source.EntityMapper
import com.example.imagesearchapp.domain.usecase.unsplashimage.entity.UnsplashImageEntity

internal object UnsplashImageMapper : EntityMapper<UnsplashImageEntity, UnsplashImage>() {

    override fun fromEntityToModel(entity: UnsplashImageEntity): UnsplashImage {
        return UnsplashImage(
            id = entity.id,
            userName = entity.userName,
            imageUrl = entity.imageUrl,
            imageDescription = entity.imageDescription,
            keyword = entity.keyword
        )
    }

    override fun fromModelToEntity(model: UnsplashImage): UnsplashImageEntity {
        return UnsplashImageEntity(
            id = model.id,
            userName = model.userName,
            imageUrl = model.imageUrl,
            imageDescription = model.imageDescription,
            keyword = model.keyword
        )
    }
}