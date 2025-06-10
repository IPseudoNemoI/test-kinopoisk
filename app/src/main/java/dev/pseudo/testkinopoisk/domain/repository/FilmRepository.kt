package dev.pseudo.testkinopoisk.domain.repository

import dev.pseudo.testkinopoisk.domain.model.Film

interface FilmRepository {
    suspend fun getFilms(): List<Film>
}
