package io.github.brunogabriel.mockpinterceptor

import android.content.Context
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Invocation
import java.io.BufferedReader
import java.io.IOException

class MockPInterceptor private constructor(
    private val context: Context,
    private val builder: Builder
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.request().let { request ->
            with(takeInterceptorBehavior(request)) {
                val jsonData = if (asset.isNotBlank()) readAssetByName(asset) else ""
                if (jsonData.isNotBlank()) {
                    if (runDelay) simulateDelay()
                    buildResponse(request, jsonData, statusCode)
                } else {
                    chain.proceed(request)
                }
            }
        }
    }

    private fun takeInterceptorBehavior(request: Request) =
        request.tag(Invocation::class.java)?.method()?.getAnnotation(MOCK::class.java)?.let {
            mockToInterceptorData(it)
        } ?: InterceptorData()

    private fun buildResponse(request: Request, jsonData: String, statusCode: Int) =
        Response.Builder().apply {
            request(request)
            protocol(Protocol.HTTP_2)
            code(statusCode)
            message("")
            body(jsonData.toResponseBody(JSON_MEDIA_TYPE.toMediaTypeOrNull()))
        }.build()

    private fun readAssetByName(assetName: String) = try {
        context.assets.open(assetName)
            .bufferedReader()
            .use(BufferedReader::readText)
    } catch (_: IOException) {
        ""
    }

    private fun simulateDelay() = Thread.sleep(
        with(builder) {
            (minDelay..maxDelay).random()
        }
    )

    class Builder(private val context: Context) {
        var minDelay: Long = DEFAULT_MIN_DELAY_IN_MS
            private set

        var maxDelay: Long = DEFAULT_MAX_DELAY_IN_MS
            private set

        fun build() = MockPInterceptor(context, this)

        fun addDelayInMillis(minDelay: Long, maxDelay: Long) = apply {
            if (minDelay <= maxDelay) {
                this.minDelay = minDelay
                this.maxDelay = maxDelay
            }
        }
    }

    private companion object {
        private const val DEFAULT_MIN_DELAY_IN_MS = 0L
        private const val DEFAULT_MAX_DELAY_IN_MS = 1_000L
        private const val JSON_MEDIA_TYPE = "application/json"
    }
}