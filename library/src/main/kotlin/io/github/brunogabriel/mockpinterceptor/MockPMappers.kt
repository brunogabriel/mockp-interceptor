package io.github.brunogabriel.mockpinterceptor

internal fun mockToInterceptorData(mock: MOCK?) = mock
    .takeIf { it != null }
    ?.let {
        InterceptorData(
            asset = it.asset,
            statusCode = it.statusCode,
            runDelay = it.runDelay
        )
    } ?: InterceptorData()