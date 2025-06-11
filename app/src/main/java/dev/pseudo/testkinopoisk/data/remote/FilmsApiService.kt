package dev.pseudo.testkinopoisk.data.remote

import dev.pseudo.testkinopoisk.data.model.FilmsResponse
import retrofit2.Response
import retrofit2.http.GET

interface FilmsApiService {
    @GET("films.json")
    suspend fun getFilms(): Response<FilmsResponse>
}