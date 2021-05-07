package ru.bakhelovs.network.data.remote.retrofit.response

import okhttp3.Headers
import java.net.HttpURLConnection

data class CatBreedResultResponse(
    val statusCode: Int = 0,
    val headers: Headers? = null,
    val message: String = "",
    val jsonResponse: String? = null
) {



    // This kind of StatusCode verification is a sample.
    // Usually, you have to handle codes more carefully.
    // According to a project's technical documentation.
    fun hasBadResult(): Boolean = jsonResponse.isNullOrEmpty()
            || (statusCode < HttpURLConnection.HTTP_OK || statusCode >= HttpURLConnection.HTTP_MULT_CHOICE)
}
