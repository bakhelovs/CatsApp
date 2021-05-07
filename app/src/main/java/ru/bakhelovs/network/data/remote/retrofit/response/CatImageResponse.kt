package ru.bakhelovs.network.data.remote.retrofit.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CatImageResponse(
    @SerialName("id")val id: String,
    @SerialName("url") val imageUrl: String
)