package ru.bakhelovs.network.data.remote.retrofit

import ru.bakhelovs.network.data.remote.RemoteDataSource

class RetrofitDataSource(private val api: CatsApiService) : RemoteDataSource {

    override suspend fun loadRandomCat() {
        TODO("Not yet implemented")
    }

    override suspend fun loadBreedNames() {
        TODO("Not yet implemented")
    }

    override suspend fun loadChosenBreed(id: String) {
        TODO("Not yet implemented")
    }
}