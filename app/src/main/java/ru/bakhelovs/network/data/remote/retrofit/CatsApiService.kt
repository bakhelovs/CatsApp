package ru.bakhelovs.network.data.remote.retrofit

import retrofit2.http.GET
import retrofit2.http.Query
import ru.bakhelovs.network.data.remote.retrofit.response.ResponseItem
import ru.bakhelovs.network.data.remote.retrofit.response.CatImageResponse
import ru.bakhelovs.network.data.remote.retrofit.response.CatsBreedNamesResponse


interface CatsApiService {

    @GET("images/search?size=small&order=RANDOM&limit=5&format=json")
    suspend fun getRandomImage(): List<CatImageResponse>

    @GET("breeds?format=json")
    suspend fun getBreedNames(): List<CatsBreedNamesResponse>

    @GET("images/search?")
    suspend fun getChosenBreedDetails(
        @Query("breed_ids") id: String
    ): List<ResponseItem>
}
