package cz.levinzonr.studypad.injection

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapterFactory
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import cz.levinzonr.studypad.BuildConfig
import cz.levinzonr.studypad.rest.Api
import cz.levinzonr.studypad.rest.AuthTokenInterceptor
import cz.levinzonr.studypad.rest.FirebaseAuthenticator
import cz.levinzonr.studypad.rest.LocaleInterceptor
import cz.levinzonr.studypad.rest.utils.ItemTypeAdaperFactory
import okhttp3.OkHttpClient
import org.koin.dsl.module.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Rest module that defines the HTTP Client
 */
val rest = module {

    single<TypeAdapterFactory> { ItemTypeAdaperFactory() }


    // Okhttp
    single<OkHttpClient> {

        val clientBuilder = OkHttpClient.Builder()
            .connectTimeout(45, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(AuthTokenInterceptor(get()))
            .addInterceptor(LocaleInterceptor(get()))
            .authenticator(FirebaseAuthenticator(get()))
        if (BuildConfig.DEBUG) {
            val logging = okhttp3.logging.HttpLoggingInterceptor()
            logging.level = okhttp3.logging.HttpLoggingInterceptor.Level.BODY
            clientBuilder.addInterceptor(logging)
        }

        clientBuilder.build()
    }


    single<Gson> {
        GsonBuilder()
            .registerTypeAdapterFactory(get())
            .create()
    }

    single<Converter.Factory> {
        GsonConverterFactory.create(get())
    }

    // Retrofit
    single<Retrofit> {
        Retrofit.Builder()
            .client(get())
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(get())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    single { GsonConverterFactory.create(get()) }

    single<Api> {
        get<Retrofit>().create<Api>(Api::class.java)
    }

}