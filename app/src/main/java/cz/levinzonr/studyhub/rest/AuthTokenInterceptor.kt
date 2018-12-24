package cz.levinzonr.studyhub.rest

import cz.levinzonr.studyhub.storage.TokenRepository
import okhttp3.Interceptor
import okhttp3.Response

class AuthTokenInterceptor(private val tokenRepository: TokenRepository) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer ${tokenRepository.getToken()}")
            .build()
        return chain.proceed(request)
    }
}