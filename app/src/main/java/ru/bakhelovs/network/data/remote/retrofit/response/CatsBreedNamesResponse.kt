package ru.bakhelovs.network.data.remote.retrofit.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CatsBreedNamesResponse(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String
    )