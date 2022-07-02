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

    override fun intercept(chain: Interceptor.Chain) = chain.proceed(chain.request())

    class Builder(private val context: Context) {
        fun build() = MockPInterceptor(context, this)
        fun addMinDelayInMillis(minDelay: Long) = this
        fun addMaxDelayInMillis(maxDelay: Long) = this
    }
}