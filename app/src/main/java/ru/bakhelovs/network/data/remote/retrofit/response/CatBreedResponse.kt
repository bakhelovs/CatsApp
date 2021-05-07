package ru.bakhelovs.network.data.remote.retrofit.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BreedsResponse(
    @SerialName("cat_friendly") val catFriendly: Int? = null,
    @SerialName("suppressed_tail") val suppressedTail: Int? = null,
    @SerialName("wikipedia_url") val wikipediaUrl: String? = null,
    @SerialName("origin") val origin: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("experimental") val experimental: Int? = null,
    @SerialName("life_span") val lifeSpan: String? = null,
    @SerialName("cfa_url") val cfaUrl: String? = null,
    @SerialName("rare") val rare: Int? = null,
    @SerialName("country_codes") val countryCodes: String? = null,
    @SerialName("lap") val lap: Int? = null,
    @SerialName("bidability") val bidability: Int? = null,
    @SerialName("id") val id: String? = null,
    @SerialName("short_legs") val shortLegs: Int? = null,
    @SerialName("shedding_level") val sheddingLevel: Int? = null,
    @SerialName("dog_friendly") val dogFriendly: Int? = null,
    @SerialName("natural") val natural: Int? = null,
    @SerialName("rex") val rex: Int? = null,
    @SerialName("health_issues") val healthIssues: Int? = null,
    @SerialName("hairless") val hairless: Int? = null,
    @SerialName("weight") val weight: WeightResponse? = null,
    @SerialName("adaptability") val adaptability: Int? = null,
    @SerialName("vocalisation") val vocalisation: Int? = null,
    @SerialName("intelligence") val intelligence: Int? = null,
    @SerialName("social_needs") val socialNeeds: Int? = null,
    @SerialName("country_code") val countryCode: String? = null,
    @SerialName("child_friendly") val childFriendly: Int? = null,
    @SerialName("temperament") val temperament: String? = null,
    @SerialName("vcahospitals_url") val vcahospitalsUrl: String? = null,
    @SerialName("grooming") val grooming: Int? = null,
    @SerialName("hypoallergenic") val hypoallergenic: Int? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("vetstreet_url") val vetstreetUrl: String? = null,
    @SerialName("indoor") val indoor: Int? = null,
    @SerialName("energy_level") val energyLevel: Int? = null,
    @SerialName("stranger_friendly") val strangerFriendly: Int? = null,
    @SerialName("reference_image_id") val referenceImageId: String? = null,
    @SerialName("affection_level") val affectionLevel: Int? = null
)

@Serializable
data class WeightResponse(
    @SerialName("metric") val metric: String? = null,
    @SerialName("imperial") val imperial: String? = null
)

@Serializable
data class ResponseItem(
    @SerialName("width") val width: Int? = null,
    @SerialName("id") val id: String? = null,
    @SerialName("url") val url: String? = null,
    @SerialName("breeds") val breeds: List<BreedsResponse>? = null,
    @SerialName("height") val height: Int? = null
)
