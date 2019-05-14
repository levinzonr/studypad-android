package cz.levinzonr.studypad.rest

import cz.levinzonr.studypad.domain.repository.TokenRepository
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

/**
 * Interceptor class that is used to attach an access token to the outgoing request
 * @param tokenRepository used to obtain the token value
 */
class AuthTokenInterceptor(private val tokenRepository: TokenRepository) : Interceptor {

    /**
     * Actual method where the interception takes place
     * @param chain current chain of requests
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        Timber.d("Token valid: ${tokenRepository.isValid()}")
        val request = chain.request()
            .newBuilder()
        tokenRepository.getToken()?.let { request.header("Firebase", it) }
        return chain.proceed(request.build())
    }
}