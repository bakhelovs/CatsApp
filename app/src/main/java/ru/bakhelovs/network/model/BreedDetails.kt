package ru.bakhelovs.network.model

import ru.bakhelovs.network.data.remote.retrofit.response.BreedsResponse

data class BreedDetails(
    val width: Int?,
    val id: String?,
    val url: String?,
    val breeds: BreedsResponse?,
    val height: Int?
)


data class Breeds(
    val catFriendly: Int,
    val suppressedTail: Int,
    val wikipediaUrl: String,
    val origin: String,
    val description: String,
    val experimental: Int,
    val lifeSpan: String,
    val cfaUrl: String,
    val rare: Int,
    val countryCodes: String,
    val lap: Int,
    val bidability: Int,
    val id: String,
    val shortLegs: Int,
    val sheddingLevel: Int,
    val dogFriendly: Int,
    val natural: Int,
    val rex: Int,
    val healthIssues: Int,
    val hairless: Int,
    val weight: Weight,
    val adaptability: Int,
    val vocalisation: Int,
    val intelligence: Int,
    val socialNeeds: Int,
    val countryCode: String,
    val childFriendly: Int,
    val temperament: String,
    val vcahospitalsUrl: String,
    val grooming: Int,
    val hypoallergenic: Int,
    val name: String,
    val vetstreetUrl: String,
    val indoor: Int,
    val energyLevel: Int,
    val strangerFriendly: Int,
    val referenceImageId: String,
    val affectionLevel: Int
)


data class Weight(
    val metric: String,
    val imperial: String
)