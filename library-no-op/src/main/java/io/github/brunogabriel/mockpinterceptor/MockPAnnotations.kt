package io.github.brunogabriel.mockpinterceptor

import java.net.HttpURLConnection

@Retention(AnnotationRetention.RUNTIME)
annotation class MOCK(
    val asset: String = "",
    val statusCode: Int = HttpURLConnection.HTTP_OK,
    val runDelay: Boolean = false
)