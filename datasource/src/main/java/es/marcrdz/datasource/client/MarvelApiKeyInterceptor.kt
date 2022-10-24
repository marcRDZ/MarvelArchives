package es.marcrdz.datasource.client

import es.marcrdz.datasource.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.math.BigInteger
import java.security.MessageDigest
import java.util.*
import kotlin.text.Charsets.UTF_8


class MarvelApiKeyInterceptor : Interceptor {

    private val md5Digester: MessageDigest = MessageDigest.getInstance("MD5")

    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val timeStamp = Date().time
        val hash = md5Digester.digest(
            "${timeStamp}${BuildConfig.MARVEL_PRIVATE_API_KEY}${BuildConfig.MARVEL_PUBLIC_API_KEY}"
                .toByteArray(UTF_8)
        )
        val url = original.url.newBuilder()
            .addQueryParameter("ts", timeStamp.toString())
            .addQueryParameter("apikey", BuildConfig.MARVEL_PUBLIC_API_KEY)
            .addQueryParameter("hash", BigInteger(1, hash).toString(16))
            .build()
        val request = original.newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }

}
