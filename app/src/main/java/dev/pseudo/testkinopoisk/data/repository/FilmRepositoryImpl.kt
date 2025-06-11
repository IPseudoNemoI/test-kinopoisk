package dev.pseudo.testkinopoisk.data.repository

import dev.pseudo.testkinopoisk.data.mapper.toDomain
import dev.pseudo.testkinopoisk.data.remote.FilmsApiService
import dev.pseudo.testkinopoisk.domain.model.Film
import dev.pseudo.testkinopoisk.domain.repository.FilmRepository

class FilmRepositoryImpl(private val api: FilmsApiService) : FilmRepository {
    override suspend fun getFilms(): List<Film> {
        return api.getFilms().body()?.films?.map { it.toDomain() } ?: emptyList()
    }
}