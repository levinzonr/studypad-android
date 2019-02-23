package cz.levinzonr.studypad.rest

import cz.levinzonr.studypad.storage.TokenRepository
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

class AuthTokenInterceptor(private val tokenRepository: TokenRepository) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        Timber.d("Token valid: ${tokenRepository.isValid()}")
        val request = chain.request()
            .newBuilder()
        tokenRepository.getToken()?.let { request.header("Firebase", it) }
        return chain.proceed(request.build())
    }
}