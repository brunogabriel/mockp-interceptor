package io.github.brunogabriel.mockpinterceptor

import android.content.Context
import okhttp3.Interceptor

@Suppress("unused", "UNUSED_PARAMETER")
class MockPInterceptor private constructor(
    private val context: Context,
    private val builder: Builder
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain) = chain.proceed(chain.request())

    class Builder(private val context: Context) {
        fun build() = MockPInterceptor(context, this)
        fun addDelayInMillis(minDelay: Long, maxDelay: Long) = this
    }
}