package io.github.brunogabriel.mockpinterceptor

import java.net.HttpURLConnection

internal class InterceptorData(
    val asset: String = "",
    val statusCode: Int = HttpURLConnection.HTTP_OK,
    val runDelay: Boolean = false
)