package dev.pseudo.testkinopoisk.data.model

import com.google.gson.annotations.SerializedName

data class FilmDto(
    val id: Int,
    @SerializedName("localized_name") val localizedName: String,
    val name: String,
    val year: Int,
    val rating: Float?,
    @SerializedName("image_url") val imageUrl: String?,
    val description: String?,
    val genres: List<String>,
)