package dev.pseudo.testkinopoisk.data.model

import com.google.gson.annotations.SerializedName

data class FilmsResponse(
    @SerializedName("films")
    val films: List<FilmDto>
)
