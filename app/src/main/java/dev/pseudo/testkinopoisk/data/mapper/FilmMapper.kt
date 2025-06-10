package dev.pseudo.testkinopoisk.data.mapper

import dev.pseudo.testkinopoisk.data.model.FilmDto
import dev.pseudo.testkinopoisk.domain.model.Film

fun FilmDto.toDomain(): Film {
    return Film(
        id = this.id,
        localizedName = this.localizedName,
        name = this.name,
        year = this.year,
        rating = this.rating,
        imageUrl = this.imageUrl,
        description = this.description,
        genres = this.genres
    )
}