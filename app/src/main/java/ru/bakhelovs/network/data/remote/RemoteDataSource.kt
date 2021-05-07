package ru.bakhelovs.network.data.remote

interface RemoteDataSource {

    suspend fun loadRandomCat()
    suspend fun loadBreedNames()
    suspend fun loadChosenBreed(id: String)

}