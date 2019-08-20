package com.test.gojek.network

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException

class LogInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val t1 = System.nanoTime()
        Logger.d(
            String.format(
                "Sending request %s on %s%n%s",
                request.url, chain.connection(), request.headers
            )
        )

        val response = chain.proceed(request)

        val t2 = System.nanoTime()
        Logger.d(
            String.format(
                "Received response for %s in %.1fms%n%s",
                response.request.url, (t2 - t1) / 1e6, response.headers
            )
        )


        val responseString = String(response.body!!.bytes())

        Logger.d("Response: $responseString")

        return response.newBuilder()
            .body(ResponseBody.create(response.body!!.contentType(), responseString))
            .build()
    }
}