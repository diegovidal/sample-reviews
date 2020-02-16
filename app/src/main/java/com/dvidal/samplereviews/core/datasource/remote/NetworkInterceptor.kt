package com.dvidal.samplereviews.core.datasource.remote

import com.dvidal.samplereviews.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author diegovidal on 2020-02-15.
 */
class NetworkInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response? {

        val modifiedRequest = chain.request()
            .newBuilder()
            .addHeader("User-Agent", USER_AGENT_VALUE)
            .build()

        return chain.proceed(modifiedRequest)
    }

    companion object {

        val USER_AGENT_VALUE: String = BuildConfig.USER_AGENT_VALUE
    }
}