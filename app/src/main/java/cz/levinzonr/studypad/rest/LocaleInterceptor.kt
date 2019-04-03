package cz.levinzonr.studypad.rest

import cz.levinzonr.studypad.domain.managers.UserManager
import cz.levinzonr.studypad.domain.repository.LocaleRepository
import okhttp3.Interceptor
import okhttp3.Response

class LocaleInterceptor(private val localeRepository: LocaleRepository) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
        localeRepository.getDefaultLocale().let { request.header("Firebase", it.code) }
        return chain.proceed(request.build())
    }
}