package cz.levinzonr.studypad.rest

import cz.levinzonr.studypad.domain.repository.LocaleRepository
import okhttp3.Interceptor
import okhttp3.Response


/**
 * An interecptor that is used to attach the information about user locale to the outgoing request
 * @param localeRepository is used to get current locale
 */
class LocaleInterceptor(private val localeRepository: LocaleRepository) : Interceptor {

    /**
     * Actual method where the interception takes place
     * @param chain current chain of requests
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
        localeRepository.getDefaultLocale().let { request.header("Locale", it.code) }
        return chain.proceed(request.build())
    }
}